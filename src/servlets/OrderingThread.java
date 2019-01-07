package servlets;

import java.util.*;

import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;

public class OrderingThread extends Thread{
	int nr;
	OrderMaker om;
	public OrderingThread(int nr) {
		nr = this.nr;
		om = new OrderMaker();
	}
	public void run() {
		ArrayList<Customer> al = new ArrayList<>();
		try {
			DataObject d = DataAccess.get("Customer", (Criteria) null);
			Iterator it = d.getRows("Customer");
			while(it.hasNext()) {
				Row r = (Row) it.next();
				int id = (Integer)r.get("CUST_ID");
				int add = (Integer)r.get("CUST_ADD");
				Customer c = new Customer(id, add, nr, om);
				c.start();
				al.add(c);
			}
			for(Customer c: al) {
				try {
					c.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
