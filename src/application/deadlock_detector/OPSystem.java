package application.deadlock_detector;

import java.util.ArrayList;
import java.util.List;

import graphDrawer.GDGraphDrawer;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/

public class OPSystem implements Runnable{
	private OPGraph graph;
	private Integer restTime;
	private List<OPResourceNode<OPResource>> resources;
	private List<OPProcessNode<OPProcess>> processes;
	private GDGraphDrawer drawer;
	
	private static OPSystem instance = null;
	public static OPSystem setInstance(Integer restTime, GDGraphDrawer drawer) {
		if(instance == null) {
			instance = new OPSystem(restTime, drawer);
		}
		return instance;
	}
	
	public static OPSystem setInstance(Integer restTime, List<OPResource> resources, GDGraphDrawer drawer) {
		if(instance == null) {
			instance = new OPSystem(restTime, resources, drawer);
		}
		return instance;
	}
	
	public static OPSystem shared() {
		return instance;
	}
	
	private OPSystem(Integer restTime, GDGraphDrawer drawer) {
		super();
		this.graph = new OPGraph();
		this.restTime = restTime;
		this.drawer = drawer;
		this.resources = new ArrayList<>();
		this.processes = new ArrayList<>();
	}
	private OPSystem(Integer restTime, List<OPResource> resources, GDGraphDrawer drawer) {
		super();
		this.graph = new OPGraph();
		this.restTime = restTime;
		this.drawer = drawer;
		this.processes = new ArrayList<>();
		this.resources = new ArrayList<>();
		
		OPResourceNode<OPResource> resourceNode;
		for (OPResource resource: resources) {
			resourceNode = new OPResourceNode<OPResource>(resource);
			this.resources.add(resourceNode);
			graph.addNode(resourceNode);
		}
		
	}

	@Override
	public void run() {
		
	}
	
	public OPGraph getGraph() {
		return graph;
	}

	public void setGraph(OPGraph graph) {
		this.graph = graph;
	}

	public Integer getRestTime() {
		return restTime;
	}

	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}

	public List<OPResourceNode<OPResource>> getResources() {
		return resources;
	}

	public void setResources(List<OPResourceNode<OPResource>> resources) {
		this.resources = resources;
	}

	public List<OPProcessNode<OPProcess>> getProcesses() {
		return processes;
	}

	public void setProcesses(List<OPProcessNode<OPProcess>> processes) {
		this.processes = processes;
	}

	public GDGraphDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(GDGraphDrawer drawer) {
		this.drawer = drawer;
	}
	
}
