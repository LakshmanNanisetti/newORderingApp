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
	private Printer p;
	public OrderingThread(int nr, Printer p){
		this.nr = nr;
		this.p = p;
		om = new OrderMaker(p);
		
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
				Customer c = new Customer(id, add, nr, om, p);
				c.start();
				al.add(c);
			}
			for(Customer c: al) {
				try {
					c.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					p.print("at join");
				}
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			p.print(e + "\n at thread making");
		}catch(Exception e) {
			p.print("get excep");
		}
	}
}
