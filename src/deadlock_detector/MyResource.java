package deadlock_detector;

import graph.GPNodeValue;
import java.util.concurrent.Semaphore;

/** it represents the resource item from the operational system*/

public class MyResource implements GPNodeValue{
	private String name;	
	private String resourceIdentifier;
	private Semaphore semaphore;
	
	public MyResource(String name, String resourceIdentifier) {
		super();
		this.name = name;
		this.resourceIdentifier = resourceIdentifier;
		this.semaphore = new Semaphore(1);
	}	

	
	public void claim() throws InterruptedException {
		semaphore.acquire();
	}
	
	public void release() {
		semaphore.release();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	@Override
	public String getStringValue() {
		return resourceIdentifier;
	}
	
}
