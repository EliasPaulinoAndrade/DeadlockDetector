package deadlock_detector;

import graph.MyNodeValue;

/* it is resposible for trying to allocate new resources from time to time*/

public class MyProcess implements MyNodeValue, Runnable{
	private String name;
	private Integer processIdentifier;
	private Double restTime;
	private Double activeTime;

	
	public MyProcess(String name, Integer processIdentifier, Double restTime, Double activeTime) {
		super();
		this.name = name;
		this.processIdentifier = processIdentifier;
		this.restTime = restTime;
		this.activeTime = activeTime;
	}

	@Override
	public String getStringValue() {
		return name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getProcessIdentifier() {
		return processIdentifier;
	}

	public void setProcessIdentifier(Integer processIdentifier) {
		this.processIdentifier = processIdentifier;
	}

	public Double getRestTime() {
		return restTime;
	}

	public void setRestTime(Double restTime) {
		this.restTime = restTime;
	}

	public Double getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Double activeTime) {
		this.activeTime = activeTime;
	}

}
