package deadlock_detector;

import graph.MyGraph;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/
public class MyOpSystem implements Runnable{
	private MyGraph graph;
	private Double restTime;
	
	public MyOpSystem(Double restTime) {
		super();
		this.graph = new MyGraph();
		this.restTime = restTime;
	}

	public MyGraph getGraph() {
		return graph;
	}

	public void setGraph(MyGraph graph) {
		this.graph = graph;
	}

	public Double getRestTime() {
		return restTime;
	}

	public void setRestTime(Double restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public void run() {
		
	}
}
