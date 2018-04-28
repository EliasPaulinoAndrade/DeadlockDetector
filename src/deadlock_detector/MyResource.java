package deadlock_detector;

import graph.MyNodeValue;

public class MyResource implements MyNodeValue{
	private String name;	
	
	public MyResource(String name) {
		super();
		this.name = name;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getStringValue() {
		return name;
	}
	
}
