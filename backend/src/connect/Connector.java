package connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Connector {
	static private Connection con;
	
	public ResultSet execute(String query) {
 
		try {			
			Class.forName("com.mysql.jdbc.Driver");
//			 con = DriverManager.getConnection("jdbc:mysql://a2plcpnl0797.prod.iad2.secureserver.net:3306/HelperQueue", "HelperQueueRoot", "HelperQueueRoot");

			 
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
					 "root",
					 "pass1");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;


	}
	
	public void close() {
		try {
			
			if (con!=null) {
				con.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

	}
	public void executeUpdate(String query) {
		 
		try {			
			Class.forName("com.mysql.jdbc.Driver");
//			  con = DriverManager.getConnection("jdbc:mysql://a2plcpnl0797.prod.iad2.secureserver.net:3306/HelperQueue", "HelperQueueRoot", "HelperQueueRoot");
				 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
						 "root",
						 "pass1");


			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}


}
