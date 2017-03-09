package odbg.org.libs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 
 * @author orlando
 *
 * @version 1.5
 */
public class OLogs {
	
	private static OLogs ologs;
	
	private static int maxSize = 100;
	
	private static boolean liveMode = false;
	
	private static LinkedList< HashMap<String, String> > logs = new LinkedList<>();
	
	private OLogs() {}
	
	public static OLogs getInstance() {
		return getObject();
	}
	
	public static OLogs getInstance(int maxSize) {
		OLogs.maxSize = maxSize;
		
		return OLogs.getObject();
	}
	
	public static OLogs getInstance(boolean liveMode) {
		OLogs.liveMode = liveMode;
		
		return OLogs.getObject();
	}

	public static OLogs getInstance(int maxSize, boolean liveMode) {
		OLogs.maxSize = maxSize;
		OLogs.liveMode = liveMode;
		
		return OLogs.getObject();
	}
	
	public void setMaxSize( int maxSize ) {
		OLogs.maxSize = maxSize;
		OLogs.removeLeftovers();
	}
	
	public void setLiveMode( boolean liveMode ) {
		OLogs.liveMode = liveMode;
	}
	
	public OLogs log(String msg) {
		this.write(" Log ", msg);
		return this;
	}
	
	public OLogs info(String msg) {
		this.write("Info ", msg);
		return this;
	}
	
	public OLogs warn(String msg) {
		this.write("Warn ", msg);
		return this;
	}
	
	public OLogs error(String msg) {
		this.write("Error", msg);
		return this;
	}
	
	public int length() {
		return OLogs.logs.size();
	}
	
	public boolean clear() {
		OLogs.logs.clear();
		
		return OLogs.logs.size() == 0;
	}
	
	public String getAllLogs() {
		// itera por todo los log y genera el formato indicado para luego unirlo todo
		return OLogs.logs.stream().map((log) -> this.toString(log)).collect(Collectors.joining("\n"));

	}
	
	public void toTxtFile() {
		File file = new File("logs.txt");
	    parsePlainText(file);
	}
	
	public void toTxtFile(String url) {
		File file = new File(url, "logs.txt");
		parsePlainText(file);
	}
	
	private void parsePlainText( File file ) {
		BufferedWriter bw = this.getLogFile(file);
    	
        try {
        	StringBuilder str = new StringBuilder();
        	str.append("\n--- Impresion del log a fecha: ")
        			.append(this.getTime())
        			.append(" ---\n\n")
        			.append(this.getAllLogs());
			
			bw.append(str.toString());
	        
	        bw.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BufferedWriter getLogFile( File file ) {
		BufferedWriter bw = null;
		try {
			if (file.exists() == false) {
				if (file.createNewFile() == false) {
					throw new Error("The file couldn't be created");
				}
			}
			bw = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return bw;
	}
	
	public JSonG toJson() {
		JSonG array = new JSonG();
		
		Iterator<HashMap<String, String>> i = OLogs.logs.iterator();
		
		while(i.hasNext()) {
			HashMap<String, String> log = (HashMap<String, String>) i.next();
			
			JSonG json = new JSonG();
			
			json
				.add("date", log.get("date"))
				.add("type", log.get("type"))
				.add("msg", log.get("msg"));
			
			array.add(json);
		}
		
		return array;
	}

	private static OLogs getObject() {
		if( OLogs.ologs == null ) {
			OLogs.ologs = new OLogs();
		}
		
		OLogs.removeLeftovers();
		
		return OLogs.ologs;
	}
	
	private void write(String type, String msg) {
		
		HashMap<String, String> log = new HashMap<String, String>();
		
		log.put("date", this.getTime());
		log.put("type", type);
		log.put("msg", msg);
		
		 if( OLogs.logs.size() >= OLogs.maxSize) {
			 OLogs.logs.removeLast();
		 }
		 OLogs.logs.addFirst(log);
		 
		 if( liveMode ) {
			 System.out.println(this.toString(log));
		 }
		
	}
	
	private String toString(HashMap<String, String> log) {
		StringBuilder str = new StringBuilder();
		str.append(log.get("date"))
			.append(" - ")
			.append(log.get("type"))
			.append(" : ")
			.append(log.get("msg"));
		
		return str.toString();
	}

	private String getTime() {
		Date date = new Date();
		DateFormat logFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       
		return logFormat.format(date);
	}
	
	private static void removeLeftovers() {
		int iterations = OLogs.logs.size() - maxSize;
		for (int i = 0; i < iterations; i++) {
			OLogs.logs.removeLast();
		}
	}
	
}
