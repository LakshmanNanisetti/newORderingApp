package util;

import java.util.HashMap;
import java.util.*;

public class RestDetails {
	private int restId, restAdd;
	private HashMap<Integer,Integer> restItems;
	public RestDetails(int restId, int restAdd, HashMap<Integer, Integer> restItems) {
		super();
		this.restId = restId;
		this.restAdd = restAdd;
		this.restItems = restItems;
	}
	public int getRestId() {
		return restId;
	}
	public int getRestAdd() {
		return restAdd;
	}
	public HashMap<Integer, Integer> getRestItems() {
		return restItems;
	}
		
}
