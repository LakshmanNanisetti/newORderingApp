package servlets;

import java.util.*;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;

public class OrderMaker {
	public synchronized boolean makeOrder(int custId, int restId, HashMap<Integer, Integer> order) {
		boolean done = false;
		Criteria cond = new Criteria(new Column("Item","REST_ID"),restId,QueryConstants.EQUAL);
		try {
			DataObject d = DataAccess.get("Item", cond);
			for(Map.Entry<Integer, Integer> me: order.entrySet()) {
				int id = me.getKey();
				int q = me.getValue();
				Criteria c = new Criteria(new Column("Item","ITEM_ID"), id, QueryConstants.EQUAL);
				Row r = d.getRow("Item",c);
				
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return done;
	}
}
