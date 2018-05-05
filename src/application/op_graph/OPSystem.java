package application.op_graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.delegate_definitions.OPProcessDelegate;
import graphDrawer.GDGraphDrawer;
import javafx.application.Platform;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/

public class OPSystem implements Runnable, OPProcessDelegate{
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
	
	@Override
	public void processWillClaimResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
		
		OPEdge waitEdge = new OPEdge(resourceNode);
		graph.addEdgeToNode(waitEdge, processNode);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					drawer.addEdgeToNodeAt(processNode.getId(), processNode.numberOfEdges() - 1);
				}catch (Exception e) {
					System.out.println("deu ruim");
				}
				
			}
		});
	}

	@Override
	public void processDidAcquireResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
	
		Integer lastEdgeIndex = processNode.numberOfEdges() - 1;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					drawer.removeEdgeFromNodeAt(processNode.getId(), lastEdgeIndex);
				}catch (Exception e) {
					System.out.println("deu ruim");
				}
			}
		});	
		
		OPEdge claimedEdge = new OPEdge(processNode);
		graph.addEdgeToNode(claimedEdge, resourceNode);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				drawer.addEdgeToNodeAt(resourceNode.getId(), resourceNode.numberOfEdges() - 1);
				
			}
		});
	}

	@Override
	public void processNeedReleaseResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
	
		Integer edgeIndex = resourceNode.numberOfEdges() - 1;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				OPSystem.shared().getDrawer().removeEdgeFromNodeAt(resourceNode.getId(), edgeIndex);
				
			}
		});
		
		graph.removeEdgeFromNode(resourceNode.getEdgeAt(resourceNode.numberOfEdges() - 1), resourceNode);
	}
	
	public void addProcess(OPProcess opProcess) {
		OPProcessNode<OPProcess> processNode = new OPProcessNode<OPProcess>(opProcess);
		opProcess.setSelfNode(processNode);
		
		processes.add(processNode);
		graph.addNode(processNode);
		
		opProcess.setDelegate(this);
		
		new Thread(this).start();
	}

	public Integer numberOfProcesses() {
		return this.processes.size();
	}
	
	public OPProcessNode<OPProcess> getProcess(Integer index) {
		return this.processes.get(index);
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

	public GDGraphDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(GDGraphDrawer drawer) {
		this.drawer = drawer;
	}
	
	public Iterable<OPProcessNode<OPProcess>> processesIterable = new Iterable<OPProcessNode<OPProcess>>() {
		
		@Override
		public Iterator<OPProcessNode<OPProcess>> iterator() {
			return new ProcessIterator();
		}
		
		class ProcessIterator implements Iterator<OPProcessNode<OPProcess>>{
			private Iterator<OPProcessNode<OPProcess>> iterator = processes.iterator();
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public OPProcessNode<OPProcess> next() {
				return iterator.next();
			}
			
		}
	};
	
	public Iterable<OPResourceNode<OPResource>> resourcesIterable = new Iterable<OPResourceNode<OPResource>>() {
		
		@Override
		public Iterator<OPResourceNode<OPResource>> iterator() {
			return new ResourceIterator();
		}
		
		class ResourceIterator implements Iterator<OPResourceNode<OPResource>>{
			private Iterator<OPResourceNode<OPResource>> iterator = resources.iterator();
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public OPResourceNode<OPResource> next() {
				return iterator.next();
			}
			
		}
	};
	
}
