

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

import com.mysql.jdbc.ResultSetMetaData;

import connect.Connector;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveStudent")
public class RemoveStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RemoveStudent() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connector c = new Connector();
		
		
		String cpFname = request.getParameter("name").split(" ")[0];
		
		ResultSet rs = c.execute("SELECT * FROM HelperQueue.Students where fname = '"+cpFname +"'");
		   java.sql.ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		   int columnsNumber = 0;
		try {
			columnsNumber = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String stuString = "";
		try {
			while(rs.next()) {
			 stuString = rs.getString(1);
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Integer stuID = Integer.parseInt(stuString);

		
		 c.executeUpdate("DELETE FROM HelperQueue.Queue WHERE studentID='"+stuID+"'");
		
		System.out.println("removed and index'd ID " +stuID);
		
		

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(" ");

		
	}


	
	

}
