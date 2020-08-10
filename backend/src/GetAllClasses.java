
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
 * Servlet implementation class ReturnClassInfo
 */
@WebServlet("/GetAllClasses")
public class GetAllClasses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllClasses() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connector c  = new Connector();

		ResultSet result = c.execute("select * FROM  `Class`");
		ArrayList<String> cinfo = new ArrayList<String>();
		try {
			
			while(result.next()){
				String prefix = result.getString("prefix");
				String cnum = result.getString("num");
				cinfo.add(prefix+" "+cnum);
			}
		}
		
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(cinfo);
		System.out.println(json);
		
		c.close();
		
		response.addHeader("Access-Control-Allow-Origin", "*");

		response.getWriter().write(json);
	}

}
