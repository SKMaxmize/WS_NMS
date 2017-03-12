package MFEC_CA_NMS_Dashboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.sql.Statement;

public class KBANK_NMS_DASHBOARD extends DBConnection {
	private static DBConnection db = new DBConnection();
	private static WebService ws = new WebService();
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public static void XML_WS(String XMLdata){
		/*
		System.out.println(ws.get_URLWebService());
		System.out.println(ws.get_UsernameWebService());
		System.out.println(ws.get_PasswordWebService());
		System.out.println(ws.get_MethodWebService()); 
		 */
		
		try {
			URL u = new URL(ws.get_URLWebService());
			URLConnection uc = u.openConnection();
	        HttpURLConnection connection = (HttpURLConnection) uc;
	        // Basic Authorization Web Service
	        byte[] encodedBytes = Base64.getEncoder().encode((ws.get_UsernameWebService()+":"+ws.get_PasswordWebService()).getBytes());          
	        String basicAuth = "Basic " + new String(encodedBytes);
	        
	        //final File folder = new File("XML/");
			//listFileForFolder(folder);
	       	//String xmldata = ReadXMlFromXMLFile();
	        
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod(ws.get_MethodWebService());
	        connection.setRequestProperty("Content-Length", Integer.toString(XMLdata.length()));
	        connection.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
	        connection.setRequestProperty ("Authorization", basicAuth);
	        
	        OutputStream out = connection.getOutputStream();
	        Writer wout = new OutputStreamWriter(out);

	        wout.write(XMLdata);
	        wout.flush();
	        wout.close();
	        
	        InputStream fis = connection.getInputStream(); 
	        InputStreamReader in = new InputStreamReader(fis, "UTF-8");
	          
	        int c;
	        while ((c = in.read()) != -1) {
	        	System.out.write(c);
	        }
	        in.close();
		}catch (IOException e) {
	          System.err.println(e); 
		}
	}
	
	public static void listFileForFolder(File folder){
		for(final File fileEntry : folder.listFiles()){
			if(fileEntry.isDirectory()){
				listFileForFolder(fileEntry);
			}else{
				System.out.println(fileEntry.getName());
				ReadXMlFromXMLFile(folder+"/"+fileEntry.getName());
			}
		}
	}
	
	public static void ReadXMlFromXMLFile(String path){
		File file = new File(path);
		ArrayList<String> myArr = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				myArr.add(line);
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		//return sb.toString();
		XML_WS(sb.toString());
	}
	
	public static void ReadParamFromConfigFile(){
		String path = "ConfigFile.txt";
		File file = new File(path);
		ArrayList<String> myArr = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				if(!line.startsWith("#")){
					myArr.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// List from ArrayList
		for (String s : myArr) {
			String[] strline = s.split("=",-1);
			if(strline[0].equals("DB_URL")){
				db.set_DB_URL(strline[1]);
			}else if(strline[0].equals("DB_USERNAME")){
				db.set_DB_USERNAME(strline[1]);
			}else if(strline[0].equals("DB_PASSWORD")){
				db.set_DB_PASSWORD(strline[1]);
			}else if(strline[0].equals("WEBSERVICE_URL")){
				ws.set_URLWebService(strline[1]);
			}else if(strline[0].equals("WEBSERVICE_USERNAME")){
				ws.set_UsernameWebService(strline[1]);
			}else if(strline[0].equals("WEBSERVICE_PASSWORD")){
				ws.set_PasswordWebService(strline[1]);
			}else if(strline[0].equals("WEBSERVICE_METHOD")){
				ws.set_MethodWebService(strline[1]);
			}
		}
	}
	
	public static Connection ConnectDB(){
		try{
            //Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");       
            //Open a connection
            System.out.println("Connecting to database... ");
            conn = DriverManager.getConnection(db.get_DB_URL(), db.get_DB_USERNAME(), db.get_DB_PASSWORD());
            if(conn != null){
            	System.out.println("Success!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
		return conn;
	}
	
	public static void InserttoDB(String GCGroup, String Severity, int Amount,  ){
		try {
			stmt = conn.createStatement();
			String SQL = "";
			stmt.executeUpdate(SQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean CloseDB(){
		try {
			conn.close();
			System.out.println("Close to database...");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public static void main(String[] args) {
		//ReadParamFromConfigFile();
		//ConnectDB();
		//XML_WS();
		//CloseDB();
		//ReadXMlFromXMLFile();
		
		
		
	}
}
