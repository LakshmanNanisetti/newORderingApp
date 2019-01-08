package servlets;

import java.util.*;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;

public class OrderMaker {
	private int id;
	private Printer p;
	public OrderMaker(Printer p) {
		id = 1;
		this.p = p;
	}
	private int getId() {
		return id++;
	}
	public synchronized boolean makeOrder(int custId, int restId, HashMap<Integer, Integer> order) {
		boolean done = true;
		Criteria cond = new Criteria(new Column("Item","REST_ID"),restId,QueryConstants.EQUAL);
		try {
			DataObject d = DataAccess.get("Item", cond);
			for(Map.Entry<Integer, Integer> me: order.entrySet()) {
				int id = me.getKey();
				int q = me.getValue();
				Criteria c = new Criteria(new Column("Item","ITEM_ID"), id, QueryConstants.EQUAL);
				Row r = d.getRow("Item",c);
				int tq = (Integer) r.get("ITEM_QUANTITY");
				if(q < tq) {
					r.set("ITEM_QUANTITY", tq-q);
					d.updateRow(r);
				}else {
					done = false;
					break;
				}
			}
			if(done) {
				DataAccess.update(d);
				d = new WritableDataObject();
				Row r = new Row("Order");
				int oid = getId();
				r.set("ORDER_ID", oid);
				r.set("CUST_ID", custId);
				r.set("REST_ID", restId);
				r.set("ORDER_DONE", false);
				p.print("order: " + oid + " by cust: " + custId + ",at rest: " + restId 
						+ "</p><p>with items: " + order.toString());				
				d.addRow(r);
				DataAccess.update(d);
				d = new WritableDataObject();
				
				for(Map.Entry<Integer, Integer> me: order.entrySet()) {
					int id = me.getKey();
					int q = me.getValue();
					r = new Row("OrderedItem");
					r.set("OITEM_ID",id);
					r.set("ORDER_ID", oid);
					r.set("ORDER_QUANTITY", q);
					d.addRow(r);
				}
				DataAccess.update(d);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print(e +"\n at order making");
		}catch(Exception e) {
			p.print("order making exception " + e);
		}
		
		return done;
	}
}
