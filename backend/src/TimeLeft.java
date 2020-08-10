
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import connect.Connector;

/**
 * Servlet implementation class TimeLeft
 */
@WebServlet("/TimeLeft")
public class TimeLeft extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String name = request.getParameter("name");
		String fname = name.split(" ")[0];
		String lname = name.split(" ")[1];
		
		
		Connection con = null;
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
						 "root",
						 "pass1");
			
			
		Statement stmt = con.createStatement();
		
		String command = "SELECT CourseProducers.timeLeft FROM CourseProducers WHERE CourseProducers.fname='"+fname+"'";
		
		ResultSet rs = stmt.executeQuery(command);
		
		String time = null;
		try {
			while (rs.next()) {
				time = rs.getString("timeLeft");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(Integer.parseInt(time));
		System.out.println(json);
		
		response.addHeader("Access-Control-Allow-Origin", "*");

		response.getWriter().write(json);
		
		
		}
		
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		finally {
		    try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		
		
		
		
	}
}