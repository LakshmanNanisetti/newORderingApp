package orderCreation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;

import util.Printer;
/**
 *
 * @author lakshman-pt2712
 */
public class Customer extends Thread{
    private int id, address, noOfItemsInOrder, nr, restId;
    private ArrayList<Integer> restItems;
    private HashMap<Integer,Integer> order;
    private OrderMaker om;
    private boolean canOrder;
    private Printer p;
    Random rand;
    public Customer(int name, int address, int nr, OrderMaker om, Printer p) {
        this.id = name;
        this.address = address;
        this.nr = nr;
        this.om = om;
        this.p = p;
        rand = new Random();
    }

    public int getname() {
        return id;
    }

    public void setname(int name) {
        this.id = name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    public void run() {
    	int tolerance = 10;
    	while(tolerance > 0) {
    		noOfItemsInOrder = rand.nextInt(2) + 1;
    		restId = rand.nextInt(nr) + 1;
    		restItems = getRestaurantItems();
    		order = initOrder(restItems);
    		if(om.makeOrder(id, restId, order)) {
    			break;
    		}
    		tolerance --;
    	}
    }

    public HashMap<Integer,Integer> initOrder(ArrayList<Integer> restItems){
        int itemno;
        HashMap<Integer,Integer> order = new HashMap<>();
        for(int j = 0; j < noOfItemsInOrder; j ++){
                int ind = rand.nextInt(restItems.size() - j);
                itemno = restItems.remove(ind);                
                int q = rand.nextInt(2) + 1;
                order.put(itemno, q);
        }
        return order;
    }

	private ArrayList<Integer> getRestaurantItems() {
		// TODO Auto-generated method stub
		Criteria cond = new Criteria(new Column("Item","REST_ID"),restId,QueryConstants.EQUAL);
		ArrayList<Integer> items = new ArrayList<Integer>();
		try {
			DataObject d = DataAccess.get("Item",cond);
			Iterator it = d.getRows("Item");
			while(it.hasNext()) {
				Row r = (Row)it.next();
				int id = (Integer)r.get("ITEM_ID");
				items.add(id);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			p.print("get restitems :" + e);
		}
		catch(Exception e) {
			p.print("normal excep at getRestaurantItems" + e);
		}
		return items;
	}
    
}
