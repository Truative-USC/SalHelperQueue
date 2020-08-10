
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

import connect.Connector;

/**
 * Servlet implementation class queue
 */
@WebServlet("/API")
public class API extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public API() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connector c  = new Connector();
		ResultSet rs = c.execute("Select * from Students");
		
		try {
			while (rs.next()) {
				System.out.println(rs.getString(2) + "  " + rs.getString(3));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
}