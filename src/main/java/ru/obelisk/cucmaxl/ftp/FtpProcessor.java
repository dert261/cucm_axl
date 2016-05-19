package ru.obelisk.cucmaxl.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.digest.DigestUtils;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.ftp.annotation.TableColumn;
import ru.obelisk.cucmaxl.ftp.annotation.TableName;
import ru.obelisk.cucmaxl.ftp.database.BaseJDBCService;
import ru.obelisk.cucmaxl.ftp.meta.MetaField;
import ru.obelisk.cucmaxl.ftp.meta.MetaRecord;
import ru.obelisk.cucmaxl.ftp.meta.MetaTable;
import ru.obelisk.cucmaxl.statistics.ModuleHeartbeat;
import ru.obelisk.cucmaxl.statistics.metrics.FtpMetrics;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Log4j2
@Data
public class FtpProcessor<T> implements Processor{
	
	private String temporaryCsvPath;
	private boolean removeAfterProcessing;
	private Class<T> tClass;
	private BaseJDBCService<T> persistenceService;
	private int BATCH_RECORDS_COUNT = 1000;
	private int TIMEOUT_SEC = 60;
	private FtpMetrics statisticModule;
	private final int CDR = 1;
	private final int CMR = 2;
			
	private Map<String, MetaTableExt> metaTableRepo = new HashMap<String, MetaTableExt>();
	
	@Data
	private class MetaTableExt {
		private final String tableName;
		private final MetaTable metaTable;
		private boolean batchWaitTimeoutEnable=false;
		private int batchWaitTimeout=0;
			
		private TimerTask currentTimerTask;
		private Timer timer = new Timer();
		
		synchronized public void period(){
			if(currentTimerTask==null){
				timer.schedule(currentTimerTask = new TimerTask() {
					@Override
					public void run() {
						if(batchWaitTimeoutEnable) batchWaitTimeout++;
						if(batchWaitTimeoutEnable && batchWaitTimeout > TIMEOUT_SEC){
							flushTable();
						}
					}
				}, 0, 1*1000);
			}	
		}
		
		synchronized public void addRecord (MetaRecord record){
			metaTable.getRecords().add(record);
			if(metaTable.getRecords().size()>=BATCH_RECORDS_COUNT){
				flushTable();
			} else {
				if(!batchWaitTimeoutEnable){
					batchWaitTimeoutEnable=true;
					period();
				}
			}	
		}
		
		synchronized public void flushTable(){
			persistToDb(metaTable);
			metaTable.getRecords().clear();
			batchWaitTimeoutEnable=false;
			batchWaitTimeout=0;
			if(currentTimerTask!=null){
				currentTimerTask.cancel();
				currentTimerTask=null;
			}
		}
	}
	
	
	public FtpProcessor(
				Class<T> tClass, 
				BaseJDBCService<T> persistenceService,
				String temporaryCsvPath, 
				boolean removeAfterProcessing,
				FtpMetrics statisticModule){
		this.temporaryCsvPath = temporaryCsvPath;
		this.removeAfterProcessing = removeAfterProcessing;
		this.tClass = tClass;
		this.persistenceService = persistenceService;
		this.statisticModule = statisticModule;
				
		File directory = new File(temporaryCsvPath);
		if(!directory.exists()){
			log.info("Create directory for CSV temporary files: {}",temporaryCsvPath);
			directory.mkdirs();
		}
	}
	
	public FtpProcessor(
				Class<T> tClass, 
				BaseJDBCService<T> persistenceService,
				String temporaryCsvPath, 
				boolean removeAfterProcessing,
				FtpMetrics statisticsCounter,
				ModuleHeartbeat heartbeatModule,
				int batchCount){
		this(tClass, persistenceService, temporaryCsvPath, removeAfterProcessing, statisticsCounter);
		this.BATCH_RECORDS_COUNT = batchCount;
	}
	
	public FtpProcessor(
			Class<T> tClass, 
			BaseJDBCService<T> persistenceService,
			String temporaryCsvPath, 
			boolean removeAfterProcessing,
			FtpMetrics statisticsCounter,
			ModuleHeartbeat heartbeatModule,
			int batchCount,
			int timeout){
		this(tClass, persistenceService, temporaryCsvPath, removeAfterProcessing, statisticsCounter);
		this.BATCH_RECORDS_COUNT = batchCount;
		this.TIMEOUT_SEC = timeout;
	}
	
	@Override
	public void process(Exchange msg) throws Exception {
		String csvFile = convertToCsv(msg);
		try{
			List<T> items = fromCsvFile(tClass, csvFile);
			addToObjectRepository(items);
			statisticModule.addFilesProcesed(1);
		} catch(Exception exception) {
			throw exception;
		} finally {
			if(removeAfterProcessing && csvFile!=null){
				File removeFile = new File(csvFile);
				if (removeFile.exists())
					removeFile.delete();
			}
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
		
	private String convertToCsv(Exchange msg) throws IOException{
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
	    	throw e;
	    }
	    
	    return fullName;
	}
	
	private void persistToDb(MetaTable tableInfo){
		long countOfInsertedRecords = tableInfo.getRecords().size();
		if(countOfInsertedRecords>0){
			String query = getBatchSqlFromMetaTableInfo(tableInfo);
			if(query!=null){
				persistenceService.execute(query);
				statisticModule.addRecordsProcesed(countOfInsertedRecords);
				log.trace("INSERT {} RECORDS", countOfInsertedRecords);
			} else {
				log.info("Generate NULL SQL query. Probable reason is incorrect CSV.", countOfInsertedRecords);
			}
		}
		
	}
	
	private void addToObjectRepository(List<T> objects){
		for(Object obj: objects){
			Class<?> c = obj.getClass();
			log.trace("Class analize {}", c.getName());
			TableName tableNameAnnotation = c.getDeclaredAnnotation(TableName.class);
			if(tableNameAnnotation!=null){
				log.trace("Found annotation {} with name of DB table {}", tableNameAnnotation, tableNameAnnotation.name());
				MetaTableExt metaTableExtended = metaTableRepo.get(tableNameAnnotation.name());
				if(metaTableExtended == null){
					MetaTable table = new MetaTable();
					table.setTableCatalog(tableNameAnnotation.catalog());
					table.setTableName(tableNameAnnotation.name());
					table.setTableSchema(tableNameAnnotation.schema());
					metaTableExtended = new MetaTableExt(tableNameAnnotation.name(), table);
					metaTableRepo.put(tableNameAnnotation.name(), metaTableExtended);
				}
				
				MetaRecord recordInfo = new MetaRecord();
				Field[] fields = c.getDeclaredFields(); 
				for (Field field : fields) {
					TableColumn columnAnnotation = field.getAnnotation(TableColumn.class);
					if(columnAnnotation!=null){
						log.trace("Found annotation {} with name of field in DB table {}", columnAnnotation, columnAnnotation.name());
						MetaField fieldInfo = new MetaField(); 
											
						field.setAccessible(true);
						try {
							fieldInfo.setName(columnAnnotation.name());
							fieldInfo.setType(field.getType());
							fieldInfo.setValue(field.get(obj));
							
							recordInfo.getFields().add(fieldInfo);
							
							log.trace("Set for DB field {} value {}", fieldInfo.getName(), fieldInfo.getValue());
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} 
				metaTableExtended.addRecord(recordInfo);
			}
		}
	}
	
	private String getBatchSqlFromMetaTableInfo(MetaTable tableInfo){
		
		StringBuilder targetQuery = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		int valuesRecordCount = 0;
		
		String nextId = "nextval('"+ tableInfo.getTableName()+"_id_seq'), ";
				
		targetQuery.append("INSERT INTO ");
		
		targetQuery.append(
				(tableInfo.getTableCatalog().length()>0 ? tableInfo.getTableCatalog()+"." : "")
				+(tableInfo.getTableSchema().length()>0 ? tableInfo.getTableSchema()+"." : "")
				+tableInfo.getTableName()
		);
		
		fields.append("(id, ");
		int size = 0;
		int targetSize = tableInfo.getRecords().get(0).getFields().size();
		for(MetaField field : tableInfo.getRecords().get(0).getFields()){
			fields.append(field.getName());
			if(targetSize > ++size)
				fields.append(", ");
		}
		fields.append(")");
		
		targetQuery.append(fields);
		
		targetQuery.append(" VALUES ");
		
		boolean isCorrectCSV = false;
		int recordSize = 0;
		int recordTargetSize = tableInfo.getRecords().size(); 
		for(MetaRecord record : tableInfo.getRecords()){
			
			isCorrectCSV = false;
			for(MetaField field : record.getFields()){
				if(field.getName().equals("cdr_record_type")){
					if(field.getValue()!=null && ((Integer)field.getValue()==CDR || (Integer)field.getValue()==CMR)){
						isCorrectCSV=true;
						break;
					}
				}
			}
			if(isCorrectCSV){
				valuesRecordCount++;
				
				StringBuilder val = new StringBuilder();
				val.append("(");
				val.append(nextId);
				
				int fieldsSize = 0;
				int targetFieldsSize = record.getFields().size();
				for(MetaField field : record.getFields()){
						
					if(field.getName().equals("date_time_connect")
							|| field.getName().equals("date_time_disconnect") 
							|| field.getName().equals("date_time_origination") 
							|| field.getName().equals("date_time_stamp")){
							
						Integer dateTimeInt = (Integer) field.getValue();
						val.append((dateTimeInt!=null && dateTimeInt > 0) ? "'"+LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTimeInt), TimeZone.getDefault().toZoneId())+"'" : "NULL");
											
					} else {
									
						if(field.getType().isAssignableFrom(Integer.class)){
							val.append((Integer)field.getValue());
						} else if(field.getType().isAssignableFrom(String.class)){
							val.append("'"+(String)field.getValue()+"'");
						}
					}
					
					
					if(targetFieldsSize > ++fieldsSize)
						val.append(", ");
						
				}
				val.append(")");
				if(recordTargetSize > ++recordSize)
					val.append(", ");
				
				values.append(val);
			}
		}
		targetQuery.append(values);
		targetQuery.append(";");
		
		return valuesRecordCount > 0 ? targetQuery.toString() : null;
	}
}
