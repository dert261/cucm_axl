package ru.obelisk.cucmaxl.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.digest.DigestUtils;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.database.models.service.BaseService;
import ru.obelisk.cucmaxl.ftp.converter.CsvToDBConverter;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Log4j2
@Data
public class FtpProcessor<T, V> implements Processor{
	
	private String temporaryCsvPath;
	private boolean removeAfterProcessing;
	private Class<T> tClass;
	private BaseService<V> persistenceService;
	private CsvToDBConverter<T,V> converter;
	
	public FtpProcessor(
				Class<T> tClass, 
				String temporaryCsvPath, 
				boolean removeAfterProcessing,
				BaseService<V> persistenceService,
				CsvToDBConverter<T,V> converter){
		this.temporaryCsvPath = temporaryCsvPath;
		this.removeAfterProcessing = removeAfterProcessing;
		this.tClass = tClass;
		this.persistenceService = persistenceService;
		this.converter = converter;
		
		File directory = new File(temporaryCsvPath);
		if(!directory.exists()){
			log.info("Create directory for CSV temporary files: {}",temporaryCsvPath);
			directory.mkdirs();
		}
	}
	
	@Override
	public void process(Exchange msg) throws Exception {
		String csvFile = convertToCsv(msg); 
		List<T> items = fromCsvFile(tClass, csvFile);
		
		persistToDb(items);
				
		if(removeAfterProcessing){
			File removeFile = new File(csvFile);
			if (removeFile.exists())
				removeFile.delete();
		}	
	}
	
	private List<T> fromCsvFile(Class<T> clazz, String filename){
		if(filename==null) return null;
		BeanListProcessor<T> rowProcessor = new BeanListProcessor<T>(clazz);

	    CsvParserSettings parserSettings = new CsvParserSettings();
	    parserSettings.setRowProcessor(rowProcessor);
	    parserSettings.setHeaderExtractionEnabled(true);
	    
	    CsvParser parser = new CsvParser(parserSettings);
	    parser.parse(new File(filename));
	    
	    return rowProcessor.getBeans();
	}
		
	private String convertToCsv(Exchange msg){
		String filename = (String) msg.getIn().getHeaders().get("CamelFileName"); 
		String fileToWrite = DigestUtils.md5Hex(LocalDateTime.now()+filename)+".csv";
		
		File csvFile = new File(temporaryCsvPath, fileToWrite);
		String fullName = csvFile.getAbsolutePath(); 
		
		try (	BufferedReader reader = new BufferedReader(new InputStreamReader(msg.getIn().getBody(InputStream.class)));
				PrintWriter writer = new PrintWriter(new FileWriter(csvFile));
		){
	    	int current = 0;
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	if (current != 1) {
		    		writer.println(line.replaceAll("\"", ""));
		        }
		        current++;
		    }
	    } catch (IOException e) {
	    	log.warn(ObeliskStringUtils.getTraceToLog(e));
	    	fullName = null;
	    }
	    
	    return fullName;
	}
	
	private void persistToDb(List<T> items){
		Iterator<T> itemsIterator = items.iterator();
		while(itemsIterator.hasNext()){
			T tItem = itemsIterator.next();
			V vItem = converter.convert(tItem);
			persistenceService.add(vItem);
		}
	}
}
