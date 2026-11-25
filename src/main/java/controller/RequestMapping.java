package controller;

import java.util.HashMap;
import java.util.Map;

import controller.user.*;

public class RequestMapping {
	private Map<String, Controller> mappings = new HashMap<String, Controller>();
	
	public void initMapping() {
		mappings.put("/user/login", new LoginController());
	}
}
