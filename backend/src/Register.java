

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import connect.Connector;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		
		Connector c  = new Connector();
		
		String stmt = "SELECT *  FROM `UnconfirmedCourseProducers` WHERE `email` LIKE '"+email+"'";
		ResultSet rs = c.execute(stmt);
		
		ArrayList<String> cinfo = new ArrayList<String>();
		
		try {
			if (rs.next()) {
				String s2 = "SELECT *  FROM `UnconfirmedCourseProducers` WHERE `email` LIKE '"+email+"'";
				ResultSet r2 = c.execute(stmt);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(cinfo);
		System.out.println(json);
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");

		response.getWriter().write(json);

	
	}

}
