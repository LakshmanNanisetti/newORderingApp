package servlets;

public class DeliveryBoy {
	private int dbId, dbAdd;
	public DeliveryBoy(int dbId, int dbAdd) {
		this.dbId = dbId;
		this.dbAdd = dbAdd;
	}
	public int getAdd() {
		return dbAdd;
	}
	public int getId() {
		return dbId;
	}
}
