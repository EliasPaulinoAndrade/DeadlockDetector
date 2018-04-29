package deadlock_detector;

import graph.MyNodeValue;
import java.util.concurrent.Semaphore;

/** it represents the resource item from the operational system*/

public class MyResource implements MyNodeValue, Runnable{
	private String name;	
	private String resourceIdentifier;
	private Semaphore semaphore;
	
	public MyResource(String name, String resourceIdentifier) {
		super();
		this.name = name;
		this.resourceIdentifier = resourceIdentifier;
		this.semaphore = new Semaphore(1);
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	@Override
	public String getStringValue() {
		return name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
