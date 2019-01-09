<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "java.io.*,util.*,orderCreation.*" %>
    <%!
    	int nc = 0, nr = 0, ndb = 0;
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
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
%>
<p>The given parameters are:</p>
<table>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
<tr>
<td>No of Customers:</td>
<td><%= nc %></td>
</tr>
<tr>
<td>No of Restaurants:</td>
<td><%= nr %></td>
</tr>
<tr>
<td>No of Delivery Boys:</td>
<td><%= ndb %></td>
</tr>
</table>
</body>
<%
Printer p = new Printer((Writer)out);
Swiggy.work(p, nc, nr, ndb);
OrderingThread ot = new OrderingThread(nr, p);
ot.start();
try {
	ot.join();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	p.print("exception at join");
}
%>
</html>