
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import connect.Connector;

/**
 * Servlet implementation class ListQueue
 */
@WebServlet("/ListQueue")
public class ListQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String course = request.getParameter("cp");
		String fname = course.split(" ")[0];
		String lname = course.split(" ")[1];
		
		
	try {
			
			
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
				 "root",
				 "pass1");

		Statement stmt = con.createStatement();
		
		ResultSet result = stmt.executeQuery("SELECT CourseProducers.CPID FROM CourseProducers WHERE CourseProducers.fname='"+fname+"' AND CourseProducers.lname='"+lname+"'");
		
		String cpID = null;
			
		while(result.next()){
			cpID = result.getString("CPID");
		}
		
		

		result = stmt.executeQuery("SELECT Queue.studentID From Queue WHERE Queue.helperID='"+cpID+"' ORDER BY Queue.position ASC");
		
		
		
		ArrayList<String> sID = new ArrayList<String>();

		while (result.next()) {
			sID.add(result.getString("studentID"));
		}
		
		
		ArrayList<String> snames = new ArrayList<String>();
		
		for(String id : sID) {
	
			result = stmt.executeQuery("SELECT Students.fname, Students.lname From Students WHERE Students.stuID='"+id+"'");
			
			while (result.next()) {
				snames.add(result.getString("fname") + " " + result.getString("lname"));
			}
		
		}
		
		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(snames);
		System.out.println(json);
		
		con.close();
		result.close();


		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(json);

			
			
		
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
		
		
	}
}

