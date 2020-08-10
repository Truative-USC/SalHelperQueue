
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

@WebServlet("/AvailableHelpers")
public class AvailableHelpers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String course = request.getParameter("course");
		String prefix = course.split(" ")[0];
		String num = course.split(" ")[1];

		
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
						 "root",
						 "pass1");
			
			
			Statement stmt = con.createStatement();
			String stmt2 = "SELECT CourseProducers.fname, CourseProducers.lname FROM Class INNER JOIN CourseProducers ON Class.classID=CourseProducers.classID AND CourseProducers.running=1 AND Class.prefix='"+prefix+"' AND Class.num='"+num+"'";
			
			ResultSet rs = stmt.executeQuery(stmt2);
			
			ArrayList<String> cinfo = new ArrayList<String>();

			try {
				while (rs.next()) {
					cinfo.add(rs.getString("fname") + " " + rs.getString("lname"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(cinfo);
			System.out.println(json);
			
			
			con.close();
			rs.close();
			
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(json);

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		finally {
		    try { con.close(); } catch (Exception e) { /* ignored */ }
		}
		
		
		
		
		
		

	}




}
