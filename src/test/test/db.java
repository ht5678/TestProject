package test.test;
import  java.sql.*;
public class db {
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost:3306/ffs4all";
	public static String user = "root";
	public static String password = "123abc!";
	static Connection conn = null;
	
	public static void main(String[] args) {
		db.startDB();
	}
	
	public static void startDB(){
		try{
			
			Class.forName(driver);
	        conn = DriverManager.getConnection(url,user,password);		
	        System.out.println(conn);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	public void endConn(){
		try {
			
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

}
