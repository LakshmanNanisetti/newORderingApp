package orderCreation;

import java.util.HashMap;

public class OrderSummary {
	private int oid, custId, restId;
	private HashMap<Integer, Integer> order;

	public OrderSummary(int oid, int custId, int restId, HashMap<Integer, Integer> order) {
		// TODO Auto-generated constructor stub
		this.oid = oid;
		this.custId = custId;
		this.restId = restId;
		this.order = order;
	}
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getRestId() {
		return restId;
	}
	public void setRestId(int restId) {
		this.restId = restId;
	}
	public HashMap<Integer, Integer> getOrder() {
		return order;
	}
	public void setOrder(HashMap<Integer, Integer> order) {
		this.order = order;
	}
	

}
