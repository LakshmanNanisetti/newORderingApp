package servlets;

import java.util.*;

import com.adventnet.ds.query.*;
import com.adventnet.persistence.*;

public class DeliveryMaker {
	private Printer p;
	public DeliveryMaker(Printer p) {
		this.p = p;
	}
	public synchronized DeliveryBoy getFreeDb(int custAdd, int restAdd) {
		int ind = 0;
		HashMap<Integer,ArrayList<Integer>> freeDbs = new HashMap<>();
		while(true) {
			freeDbs.putAll(getFreeDbs());
			int i = restAdd,j = restAdd;
	        while(i >= 1 || j <= 10){
	        	if( i >= 1 && (freeDbs.get(i).size() != 0)) {
	        		ind = i;
	        	}
	        	else if( j <= 10 && (freeDbs.get(j).size() != 0)) {
	        		ind = j;
	        	}
	        	if(ind != 0) {
	        		break;
	        	}
	        	i --;
	        	j ++;
	        }
	        if(ind != 0) {
	        	break;
	        }else {
	        	try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					p.print(""+e);
				}
	        }
		}
		
		int dbId = freeDbs.get(ind).get(0);
		int dbAdd = ind;
		Criteria cond = new Criteria(new Column("DeliveryBoy","DB_ID"), dbId, QueryConstants.EQUAL);
		try {
			DataObject d = DataAccess.get("DeliveryBoy",cond);
			Row r = d.getFirstRow("DeliveryBoy");
			r.set("DB_BUSY", true);
			r.set("DB_ADD", custAdd);
			d.updateRow(r);
			DataAccess.update(d);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print(""+e);
		}catch(Exception e) {
			p.print("worst");
		}
		return new DeliveryBoy(dbId,dbAdd);
	}
	public HashMap<Integer,ArrayList<Integer>> getFreeDbs() {
		HashMap<Integer,ArrayList<Integer>> freeDbs = new HashMap<>();
		for(int i=1;i<=10;i++) {
			freeDbs.put(i, new ArrayList<Integer>());
		}
		Criteria cond = new Criteria(new Column("DeliveryBoy","DB_BUSY"),false, QueryConstants.EQUAL);
		try {
			DataObject d = DataAccess.get("DeliveryBoy",cond);
			Iterator it = d.getRows("DeliveryBoy");
			while(it.hasNext()) {
				Row r = (Row) it.next();
				int dbId = (Integer) r.get("DB_ID");
				int dbAdd = (Integer) r.get("DB_ADD");
				freeDbs.get(dbAdd).add(dbId);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print(""+e);
		}catch(Exception e) {
			p.print("worst");
		}
		return freeDbs;
	}
	public synchronized void wakeUp() {
		// TODO Auto-generated method stub
		notify();
	}
}
