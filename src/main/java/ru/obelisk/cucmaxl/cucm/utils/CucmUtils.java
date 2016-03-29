package ru.obelisk.cucmaxl.cucm.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class CucmUtils {
	private static final Set<String> modelType= new HashSet<String>(0);
	
	public CucmUtils(){
		initModelType();
	}
	
	private void initModelType(){
		modelType.add("Analog Phone");
		modelType.add("Cisco 12 S");
		modelType.add("Cisco 12 SP");
		modelType.add("Cisco 12 SP+");
		modelType.add("Cisco 30 SP+");
		modelType.add("Cisco 30 VIP");
		modelType.add("Cisco 3905");
		modelType.add("Cisco 3911");
		modelType.add("Cisco 3951");
		modelType.add("Cisco 6901");
		modelType.add("Cisco 6911");
		modelType.add("Cisco 6921");
		modelType.add("Cisco 6941");
		modelType.add("Cisco 6945");
		modelType.add("Cisco 6961");
		modelType.add("Cisco 7811");
		modelType.add("Cisco 7821");
		modelType.add("Cisco 7841");
		modelType.add("Cisco 7861");
		modelType.add("Cisco 7902");
		modelType.add("Cisco 7905");
		modelType.add("Cisco 7906");
		modelType.add("Cisco 7910");
		modelType.add("Cisco 7911");
		modelType.add("Cisco 7912");
		modelType.add("Cisco 7920");
		modelType.add("Cisco 7921");
		modelType.add("Cisco 7925");
		modelType.add("Cisco 7926");
		modelType.add("Cisco 7931");
		modelType.add("Cisco 7935");
		modelType.add("Cisco 7936");
		modelType.add("Cisco 7937");
		modelType.add("Cisco 7940");
		modelType.add("Cisco 7941");
		modelType.add("Cisco 7941G-GE");
		modelType.add("Cisco 7942");
		modelType.add("Cisco 7945");
		modelType.add("Cisco 7960");
		modelType.add("Cisco 7961");
		modelType.add("Cisco 7961G-GE");
		modelType.add("Cisco 7962");
		modelType.add("Cisco 7965");
		modelType.add("Cisco 7970");
		modelType.add("Cisco 7971");
		modelType.add("Cisco 7975");
		modelType.add("Cisco 7985");
		modelType.add("Cisco 8811");
		modelType.add("Cisco 8831");
		modelType.add("Cisco 8841");
		modelType.add("Cisco 8851");
		modelType.add("Cisco 8851NR");
		modelType.add("Cisco 8861");
		modelType.add("Cisco 8941");
		modelType.add("Cisco 8945");
		modelType.add("Cisco 8961");
		modelType.add("Cisco 9951");
		modelType.add("Cisco 9971");
		modelType.add("Cisco ATA 186");
		modelType.add("Cisco ATA 187");
		modelType.add("Cisco ATA 190");
		modelType.add("Cisco Dual Mode for Android");
		modelType.add("Cisco Dual Mode for iPhone");
		modelType.add("Cisco DX650");
		modelType.add("Cisco DX70");
		modelType.add("Cisco DX80");
		modelType.add("Cisco E20");
		modelType.add("Cisco IP Communicator");
		modelType.add("Cisco Jabber for Tablet");
		modelType.add("Cisco Unified Mobile Communicator");
		modelType.add("Cisco Unified Personal Communicator");
		modelType.add("Cisco VGC Phone");
		modelType.add("Cisco VGC Virtual Phone");
		modelType.add("CTI Port");
		modelType.add("H.323 Phone");
		modelType.add("Remote Destination Profile");
		modelType.add("SCCP Phone");
		modelType.add("SPA8800");
		modelType.add("Third-party AS-SIP Endpoint");
		modelType.add("Third-party SIP Device (Advanced)");
		modelType.add("Third-party SIP Device (Basic)");
	}

	public static Set<String> getModelType() {
		return modelType;
	}
	
	public String modelTypeToString(){
		//if(modelType.isEmpty()) this.initModelType();
		StringBuilder result = new StringBuilder();
		Iterator<String> iterator = modelType.iterator();
		while(iterator.hasNext()){
			String model = iterator.next();
			result.append("'"+model+"'");
			if(iterator.hasNext()) result.append(",");
		}
		return result.toString();
	}
}
