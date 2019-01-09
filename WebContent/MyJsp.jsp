<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "java.io.*,
    	java.util.*,
    	util.*,
    	orderCreation.*,
     	com.adventnet.ds.query.*,
     	com.adventnet.persistence.*" %>
    <%!
    	int nc = 0, nr = 0, ndb = 0;
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Summary</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
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
<div class="limiter">
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
					<thead>
						<tr class="table100-head">
							<th class="column1">Type</th>
							<th class="column2">Count</th>
						</tr>
						</thead>
					<tr>
					<td class="column1">No of Customers:</td>
					<td class="column2"><%= nc %></td>
					</tr>
					<tr>
					<td class="column1">No of Restaurants:</td>
					<td class="column2"><%= nr %></td>
					</tr>
					<tr>
					<td class="column1">No of Delivery Boys:</td>
					<td class="column2"><%= ndb %></td>
					</tr>
					</table>
				</div>
			</div>
		</div>
</div>
<%
Printer p = new Printer( (Writer)out);
Swiggy.work(p, nc, nr, ndb);
OrderingThread ot = new OrderingThread(nr, p);
ot.start();
try {
	ot.join();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	p.print ("exception at join");
}
%>
<h2>Orders Made are:</h2>
<table>
<tr>
<th>OrderId</th><th>Customer</th><th>Restaurant</th><th>Items</th>
</tr>
<% for (OrderSummary osum: Swiggy.os) { %>
<tr>
<td><%= osum.getOid() %></td>
<td><%= osum.getCustId() %></td>
<td><%= osum.getRestId() %></td>
<td>
<% HashMap<Integer,Integer> hm = osum.getOrder();
StringBuilder op = new StringBuilder();
for (Map.Entry<Integer,Integer> me: hm.entrySet()){
	op.append (me.getKey() + ":" + me.getValue()+"\n");
}
out.print(op.toString());
%>
</td>
</tr>
<%} %>
</table>
	</body>
</html>