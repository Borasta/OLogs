package odbg.org.main;

import odbg.org.libs.OLogs;

public class Main {

	public static void main(String[] args) {
		
		OLogs log = OLogs.getInstance(4, true);
	
		log.warn("Warning")
			.log("Log");
		
		log.error("Error")
			.info("Info");
		
		log.setLiveMode(false);
		
		System.out.println(log.toJson().getPrettyJson());
		
		log.setMaxSize(2);
		
		System.out.println(log.getAllLogs());
		
		System.out.println(log.toJson().getPrettyJson());
		
		log.toTxtFile();
		
	}

}
