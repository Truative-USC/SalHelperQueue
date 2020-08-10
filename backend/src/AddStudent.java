
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import connect.Connector;

/**
 * Servlet implementation class AddStudent
 */
@WebServlet("/AddStudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cname = request.getParameter("cp");
		String sname = request.getParameter("student");
		String cpfname = cname.split(" ")[0];
		String cplname = cname.split(" ")[1];
		String stfname = sname.split(" ")[0];
		
		//String stlname = sname.split(" ")[1];
		
		Connector c = new Connector();
		String stmt = "SELECT CourseProducers.CPID FROM CourseProducers WHERE CourseProducers.fname='"+cpfname+"' AND CourseProducers.lname='"+cplname+"'";

		ResultSet result = new Connector().execute(stmt);
		String cpID = null;
		try {
			
			while(result.next()){
				cpID = result.getString("CPID");
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
		stmt = "SELECT Students.stuID FROM Students WHERE Students.fname='"+stfname+"'";
		result = new Connector().execute(stmt);
		String stuID = null;
		try {
			
			while(result.next()){
				stuID = result.getString("stuID");
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
		stmt ="SELECT Queue.position FROM Queue WHERE Queue.helperID = '"+cpID+"' ORDER BY Queue.position DESC LIMIT 1";
		result = new Connector().execute(stmt);
		Integer pos = null;
		try {

			while(result.next()){
				pos = result.getInt("position");
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		if(pos == null) {
			pos=0;
		}
		System.out.println("position: " + pos);
		pos +=1;
		String newPos = pos.toString();
		
		stmt="INSERT INTO Queue (helperID, studentID, position)"
				+ " VALUES ('"+cpID+"', '"+stuID+"', '"+newPos+"' );";
		new Connector().executeUpdate(stmt);
		
		
		ObjectMapper om = new ObjectMapper();
		
		
		String json = om.writeValueAsString("success");
	
		System.out.println(json);
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(json);
	}
}