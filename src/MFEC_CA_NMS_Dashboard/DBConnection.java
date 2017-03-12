package MFEC_CA_NMS_Dashboard;

public class DBConnection {
	private String DB_URL;
	private String DB_USERNAME;
	private String DB_PASSWORD;
	
	public String get_DB_URL(){
		return DB_URL;
	}
	
	public void set_DB_URL(String DB_URL){
		this.DB_URL = DB_URL;
	}
	
	public String get_DB_USERNAME(){
		return DB_USERNAME;
	}
	
	public void set_DB_USERNAME(String DB_USERNAME){
		this.DB_USERNAME = DB_USERNAME;
	}
	
	public String get_DB_PASSWORD(){
		return DB_PASSWORD;
	}
	
	public void set_DB_PASSWORD(String DB_PASSWORD){
		this.DB_PASSWORD = DB_PASSWORD;
	}
}
