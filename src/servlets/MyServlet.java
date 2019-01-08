package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
        int nc = 0,ndb = 0,nr = 0;
        PrintWriter out = null;
        try{
            nc = Integer.parseInt(request.getParameter("noOfCustomers"));
            nr = Integer.parseInt(request.getParameter("noOfRestaurants"));
            ndb = Integer.parseInt(request.getParameter("noOfDeliveryBoys"));
        }
        catch(NumberFormatException nfe){
            nfe.printStackTrace();
            try{
                response.sendRedirect("index.html");
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        try{
            /* TODO output your page here. You may use following sample code. */
            out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Input Details</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>No of Customers:</td><td>" +
                    request.getParameter("noOfCustomers") + "</td></tr>");
            out.println("<tr><td>No of Restaurants:</td><td>" + 
                    request.getParameter("noOfRestaurants") + "</td></tr>");
            out.println("<tr><td>No of Delivery Boys:</td><td>" 
                    + request.getParameter("noOfDeliveryBoys") + "</td></tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Printer p = new Printer(out);
        Swiggy.work(out, nc, nr, ndb);
        
        OrderingThread ot = new OrderingThread(nr, p);
        ot.start();
        try {
			ot.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DeliveringThread dt = new DeliveringThread(p);
        dt.start();
        try {
			dt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void initCustomers(int nc){
        for(int i = 1; i <= nc; i++){
            
        }
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
