

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connect.Connector;

/**
 * Servlet implementation class studentCheck
 */
@WebServlet("/studentCheck")
public class studentCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public studentCheck() {
        super();
    }
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connector c = new Connector();

		String email = request.getParameter("email");
		System.out.println(email);
		
		
		ResultSet rs = c.execute("SELECT * FROM HelperQueue.CourseProducers where email = '"+email+ "'");
		
		boolean foundInCp = false;
		boolean foundInStudent = false;
		try {
			System.out.println( "cp size " + rs.getFetchSize());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(rs.next()) {
				foundInCp = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(foundInCp==false) {
			
			String command = "SELECT * FROM HelperQueue.Students where email='"+email+"'";
			System.out.println(command);

			rs = c.execute(command);
			
			try {
				System.out.println("Student " + rs.getFetchSize());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				if(rs.next()) {
					foundInStudent = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
			
			
			
		}
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");

		if(foundInCp == false && foundInStudent == true) {
			System.out.println("Student");
			
			response.getWriter().write("student");

		}
		else if(foundInCp == true && foundInStudent == false) {
			System.out.println("Cp");
			
			response.getWriter().write("helper");

			
		}
		else {
			System.out.println("not found");
			
			response.getWriter().write("-1");

		}
		
		
		
	}

	

}