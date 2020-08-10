

import java.io.IOException;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.PreparedStatement;

import connect.Connector;

@WebServlet("/ToggleTimer")
public class ToggleTimer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ToggleTimer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String email = request.getParamter("email");

		int pauseToggle = Integer.parseInt(request.getParameter("pause"));
			

		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HelperQueue"+ "?useSSL=false",
					 "root",
					 "pass1");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * FROM  `CourseProducers`");

			int running = -1;
			int paused = -1;
			while (rs.next()) {
				if (rs.getString("email").equals(email)) {
					running = rs.getInt("running");
					paused = rs.getInt("paused");
				}
			}
			
			int newRunning = running;
			int newPaused = 0;
			
			
			if(pauseToggle == 0) {
				if(running == 0) {
					newRunning = 1;
					newPaused = 0;
				}
				
				else {
					newRunning = 0;
					newPaused = 0;
				}
			}
			else if(pauseToggle == 1) {
				if(paused == 0) {
					newPaused = 1;
				}
				else {
					newPaused = 0;
				}
			}

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(
				      "UPDATE CourseProducers\n" + 
				      "SET paused = "+ newPaused+", running = "+ newRunning+"\n" + 
				      "WHERE email = '" + email + "';");
		    ps.executeUpdate();
		    System.out.println("Updated");
		    

			con.close();
			rs.close();
			ps.close();

			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(" ");

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}


}
