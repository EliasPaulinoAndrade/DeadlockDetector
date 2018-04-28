package deadlock_detector;

import graph.MyNodeValue;
import java.util.concurrent.Semaphore;

public class MyResource implements MyNodeValue, Runnable{
	private String name;	
	private Semaphore semaphore;
	
	public MyResource(String name) {
		super();
		this.name = name;
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

	@Override
	public String getStringValue() {
		return name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
