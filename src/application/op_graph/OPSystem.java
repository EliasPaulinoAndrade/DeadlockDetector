package application.op_graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.delegate_definitions.OPProcessDelegate;
import application.delegate_definitions.OPSystemDelegate;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/

public class OPSystem implements Runnable, OPProcessDelegate{
	private OPGraph graph;
	private Integer restTime;
	private List<OPResourceNode<OPResource>> resources;
	private List<OPProcessNode<OPProcess>> processes;
	
	private OPSystemDelegate delegate;
	
	private static OPSystem instance = null;
	public static OPSystem setInstance(Integer restTime) {
		if(instance == null) {
			instance = new OPSystem(restTime);
		}
		return instance;
	}
	
	public static OPSystem setInstance(Integer restTime, List<OPResource> resources) {
		if(instance == null) {
			instance = new OPSystem(restTime, resources);
		}
		return instance;
	}
	
	public static OPSystem shared() {
		return instance;
	}
	
	private OPSystem(Integer restTime) {
		super();
		this.graph = new OPGraph();
		this.restTime = restTime;
		this.resources = new ArrayList<>();
		this.processes = new ArrayList<>();	
	}
	
	private OPSystem(Integer restTime, List<OPResource> resources) {
		super();
		this.graph = new OPGraph();
		this.restTime = restTime;
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
		
		if(delegate != null) {
			delegate.systemAppendedEdgeToNode(this, processNode);
		}
		
		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
	}

	@Override
	public void processDidAcquireResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
	
		if(delegate != null) {
			delegate.systemWillRemoveLastEdgeFromNode(this, processNode);
		}
		
		graph.removeEdgeFromNode(processNode.getEdgeAt(processNode.numberOfEdges() - 1), processNode);
		
		OPEdge claimedEdge = new OPEdge(processNode);
		graph.addEdgeToNode(claimedEdge, resourceNode);
		
		if(delegate != null) {
			delegate.systemAppendedEdgeToNode(this, resourceNode);
		}
		
		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
	}

	@Override
	public void processNeedReleaseResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
	
		if(delegate != null) {
			delegate.systemWillRemoveLastEdgeFromNode(this, resourceNode);
		}
		
		graph.removeEdgeFromNode(resourceNode.getEdgeAt(resourceNode.numberOfEdges() - 1), resourceNode);
		

		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
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
	
	public OPSystemDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(OPSystemDelegate delegate) {
		this.delegate = delegate;
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
