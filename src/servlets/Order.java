package servlets;

import com.adventnet.ds.query.*;
import com.adventnet.persistence.*;

public class Order extends Thread{
	private int orderId, custId, restId;
	private Printer p;
	private DeliveryMaker dm;
	public Order(int orderId, int custId, int restId, Printer p, DeliveryMaker dm) {
		this.orderId = orderId;
		this.custId = custId;
		this.restId = restId;
		this.p = p;
		this.dm = dm;
	}
	public void run() {
		Criteria cond = new Criteria(new Column("Restaurant", "REST_ID"), restId, QueryConstants.EQUAL);
		try {
			DataObject d = DataAccess.get("Restaurant", cond);
			Row r = d.getFirstRow("Restaurant");
			int restAdd = (Integer)r.get("REST_ADD");
			
			cond = new Criteria(new Column("Customer","CUST_ID"), custId, QueryConstants.EQUAL);
			d = DataAccess.get("Customer", cond);
			r = d.getFirstRow("Customer");
			int custAdd = (Integer)r.get("CUST_ADD");
			DeliveryBoy db = dm.getFreeDb(custAdd, restAdd);
			p.print("db: " + db.getId() + " is delivering for cust"+ custId + " from rest" + restId);
			try {
				sleep(Math.abs(custAdd - restAdd) * 100);
				sleep(Math.abs(db.getAdd() - restAdd) * 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				p.print(""+e);
			}
			cond = new Criteria(new Column("DeliveryBoy","DB_ID"), db.getId(), QueryConstants.EQUAL);
			d = DataAccess.get("DeliveryBoy",cond);
			r = d.getFirstRow("DeliveryBoy");
			r.set("DB_BUSY", false);
			d.updateRow(r);
			DataAccess.update(d);
			dm.wakeUp();
			p.print("db" + db.getId() + " is now at " + custAdd);
			cond = new Criteria(new Column("Order","ORDER_ID"), orderId, QueryConstants.EQUAL);
			d = DataAccess.get("Order", cond);
			r = d.getFirstRow("Order");
			r.set("DB_ID", db.getId());
			d.updateRow(r);
			DataAccess.update(d);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print("Order thread " + e);
		}
		catch(Exception e) {
			p.print("order exception");
		}
	}
}
