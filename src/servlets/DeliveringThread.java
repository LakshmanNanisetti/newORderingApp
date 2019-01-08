package servlets;

import java.util.*;
import com.adventnet.ds.query.*;
import com.adventnet.persistence.*;

public class DeliveringThread extends Thread{
	private ArrayList<Order> orders;
	private Printer p;
	private DeliveryMaker dm;
	public DeliveringThread(Printer p) {
		orders = new ArrayList<>();
		this.p = p;
		dm = new DeliveryMaker(p);
	}
	public void run() {
		try {
			DataObject d = DataAccess.get("Order", (Criteria) null);
			Iterator it = d.getRows("Order");
			while(it.hasNext()) {
				Row r = (Row) it.next();
				int orderId = (Integer)r.get("ORDER_ID");
				int custId = (Integer)r.get("CUST_ID");
				int restId = (Integer)r.get("REST_ID");
				Order o = new Order(orderId, custId, restId, p, dm);
				o.start();
				orders.add(o);
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print("" + e);
		}catch(Exception e) {
			p.print("" + e);
		}
		for(Order o: orders) {
			try {
				o.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				p.print("" + e);
			}
		}
	}
}
