import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connector;
public class userTime {

	public void startTimer() throws SQLException, ClassNotFoundException {

				
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
					 "root",
					 "pass1");


		while(true) {
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update CourseProducers SET timeLeft = timeLeft - 1 where paused = 0");

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CourseProducers where timeLeft = 0 or timeLeft < 0");
			

			
			while(rs.next()) {
				
				int cpID = rs.getInt(1);
				System.out.println(rs.getInt(1));
				
				try {

					Statement stmt2 = con.createStatement();

					stmt2.executeUpdate("delete from Queue where helperID = '"+cpID+"' and position = 1");

					ResultSet temp = stmt2.executeQuery("select * from Queue where helperID ='"+cpID+"'");

					while(temp.next()) {
						stmt2.executeUpdate("update Queue SET position = position - 1 where idQueue = '"+temp.getInt(1) +"'");
					}
					
					stmt2.executeUpdate("update CourseProducers SET timeLeft = 900 where cpID = '"+cpID+"'");
					
					stmt2.close();
					temp.close();
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("decremented the time");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("error waiting");
			}
			
		}
				
		
	}
	
	
	public static void main(String [] args) throws SQLException, ClassNotFoundException {
		userTime time = new userTime();
		
		time.startTimer();
	}
	
}
