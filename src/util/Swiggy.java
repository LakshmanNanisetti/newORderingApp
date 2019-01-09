package util;
import com.adventnet.ds.query.Criteria;
import java.io.PrintWriter;
import com.adventnet.persistence.*;

import orderCreation.OrderSummary;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lakshman-pt2712
 */
public class Swiggy {
    public static ArrayList<OrderSummary> os = new ArrayList<>();
    public static ArrayList<UserDetails> custs = new ArrayList<>();
    public static ArrayList<RestDetails> rests = new ArrayList<>();
    public static ArrayList<UserDetails> dboys = new ArrayList<>();
    public static void work(Printer p, int nc, int nr, int ndb){
    	deleteWholeData(p);
        initCustomers(p, nc);
        initRestaurants(p,nr);
        initDeliveryBoys(p,ndb);
    }

    private static void initDeliveryBoys(Printer p, int ndb) {
    	DataObject dobj = null;
        Random rand = new Random();
        int areaNo;
        try {
        	dobj = new WritableDataObject();
        	for(int i = 1; i <= ndb; i++) {
        		areaNo = rand.nextInt(10) + 1;
        		Row r = new Row("DeliveryBoy");
        		r.set("DB_ID", i);
        		r.set("DB_ADD", areaNo);
        		r.set("DB_BUSY", false);
        		dobj.addRow(r);
        		dboys.add(new UserDetails(i,areaNo));
        	}
            DataAccess.update(dobj);
        } catch (DataAccessException ex) {
            p.print(ex + "\t at init Restaurants\n");
        }
		
	}
	
    private static void initRestaurants(Printer p, int nr) {
    	DataObject dobj = null;
        
        Random rand = new Random();
        int areaNo;
        try {
        	dobj = new WritableDataObject();
        	for(int i = 1; i <= nr; i++) {
        		areaNo = rand.nextInt(10) + 1;
        		Row r = new Row("Restaurant");
        		r.set("REST_ID", i);
        		r.set("REST_ADD", areaNo);
        		dobj.addRow(r);
        		
        		int itemno;
                ArrayList<Integer> itemnos = new ArrayList<Integer>();
                for (int j = 1; j <= 5; j++) {
                    itemnos.add(j);
                }
                HashMap<Integer,Integer> restItems = new HashMap<>();
                for (int j = 0; j < 3; j++) {
                	int ind = rand.nextInt(5-j);
                	itemno = itemnos.remove(ind);
                	int q = rand.nextInt(2) + 2;
                	r = new Row("Item");
                	r.set("ITEM_ID", itemno);
                	r.set("REST_ID", i);
                	r.set("ITEM_QUANTITY", q);
                	dobj.addRow(r);
                	restItems.put(itemno, q);
                }
                rests.add(new RestDetails(i,areaNo,restItems));
        	}
            DataAccess.update(dobj);
        } catch (DataAccessException ex) {
            p.print(ex + "\t at init Restaurants\n");
        }
		
	}

	public static void deleteWholeData(Printer p) {
    	DataObject dobj = null;
        try {
           dobj = DataAccess.get("Customer",(Criteria) null);
           dobj.deleteRows("Customer",(Criteria) null);
           DataAccess.update(dobj);
           
           dobj = DataAccess.get("Restaurant",(Criteria) null);
           dobj.deleteRows("Restaurant",(Criteria) null);
           DataAccess.update(dobj);
           
           dobj = DataAccess.get("DeliveryBoy",(Criteria) null);
           dobj.deleteRows("DeliveryBoy",(Criteria) null);
           DataAccess.update(dobj);
           
           dobj = DataAccess.get("Order",(Criteria) null);
           dobj.deleteRows("Order",(Criteria) null);
           DataAccess.update(dobj);
           
           dobj = DataAccess.get("Item",(Criteria) null);
           dobj.deleteRows("Item",(Criteria) null);
           DataAccess.update(dobj);

           dobj = DataAccess.get("OrderedItem",(Criteria) null);
           dobj.deleteRows("OrderedItem",(Criteria) null);
           DataAccess.update(dobj);
           
       } catch (DataAccessException ex) {
           p.print(ex + "\t at deletion\n");
       }
    }

    public static void initCustomers(Printer p, int nc) {
    	DataObject dobj = null;
        
        Random rand = new Random();
        int areaNo;
        try {
        	dobj = new WritableDataObject();
        	for(int i = 1; i <= nc; i++) {
        		areaNo = rand.nextInt(10) + 1;
        		Row r = new Row("Customer");
        		r.set("CUST_ID", i);
        		r.set("CUST_ADD", areaNo);
        		dobj.addRow(r);
        		custs.add(new UserDetails(i,areaNo));
        	}
            DataAccess.update(dobj);
        } catch (DataAccessException ex) {
            p.print(ex + "\t at init customers\n");
        }
    }
    public static String HMToString(HashMap<Integer, Integer> hm) {
    	StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> me: hm.entrySet()){
			sb.append (me.getKey() + ":" + me.getValue()+"\n");
		}
		String temp = sb.toString();
		return temp;
    }
}
