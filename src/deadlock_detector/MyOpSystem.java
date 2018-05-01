package deadlock_detector;

import java.util.ArrayList;
import java.util.List;

import application.graphDrawer.MyGraphDrawer;
import graph.MyGraph;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/
public class MyOpSystem implements Runnable{
	private MyOpGraph graph;
	private Integer restTime;
	private List<MyResourceNode<MyResource>> resources;
	private List<MyProcessNode<MyProcess>> processes;
	private MyGraphDrawer drawer;
	
	public MyOpSystem(Integer restTime, MyGraphDrawer drawer) {
		super();
		this.graph = new MyOpGraph();
		this.restTime = restTime;
		this.drawer = drawer;
		this.resources = new ArrayList<>();
		this.processes = new ArrayList<>();
	}
	
	public MyOpSystem(Integer restTime, List<MyResource> resources, MyGraphDrawer drawer) {
		super();
		this.graph = new MyOpGraph();
		this.restTime = restTime;
		this.drawer = drawer;
		this.processes = new ArrayList<>();
		this.resources = new ArrayList<>();
		
		MyResourceNode<MyResource> resourceNode;
		for (MyResource resource: resources) {
			resourceNode = new MyResourceNode<MyResource>(resource);
			this.resources.add(resourceNode);
			graph.addNode(resourceNode);
		}
		
	}

	@Override
	public void run() {
		
	}
	
	public MyOpGraph getGraph() {
		return graph;
	}

	public void setGraph(MyOpGraph graph) {
		this.graph = graph;
	}

	public Integer getRestTime() {
		return restTime;
	}

	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}

	public List<MyResourceNode<MyResource>> getResources() {
		return resources;
	}

	public void setResources(List<MyResourceNode<MyResource>> resources) {
		this.resources = resources;
	}

	public List<MyProcessNode<MyProcess>> getProcesses() {
		return processes;
	}

	public void setProcesses(List<MyProcessNode<MyProcess>> processes) {
		this.processes = processes;
	}

	public MyGraphDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(MyGraphDrawer drawer) {
		this.drawer = drawer;
	}
	
	
	
}
