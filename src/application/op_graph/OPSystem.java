package application.op_graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Semaphore;

import application.op_graph.delegates.OPProcessDelegate;
import application.op_graph.delegates.OPSystemDelegate;
import graph.GPNode;

/*it represents the operational system, it is resposible by detecting deadlocks with a cicle algorithm, and finish them.*/

public class OPSystem implements Runnable, OPProcessDelegate{
	private OPGraph graph;
	private Integer restTime;
	private Semaphore semaphoreGraph = new Semaphore(1);
	private List<OPResourceNode<OPResource>> resources;
	private List<OPProcessNode<OPProcess>> processes;
	private List<GPNode<?>> nodes;
	private Stack<GPNode<?>> nodesStack;
	
	private OPSystemDelegate delegate;
	
	private static OPSystem instance = null;
	public static OPSystem setInstance(Integer restTime) {
		if(instance == null) {
			instance = new OPSystem(restTime);
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
		this.nodes = new ArrayList<>();
		this.nodesStack = new Stack<>();
	}
	
	@Override
	public void run() {
		while(true)
		{
			for(int i = 0; i < this.nodes.size(); i++)
			{
				checkForDeadLocks(this.nodes.get(i));
				this.nodesStack.clear();
			}
			
			for(int i = 0; i < this.processes.size(); i++)
			{
				System.out.print(this.processes.get(i).getStatus() + " - ");
			}
			System.out.println();
			
			for(int i = 0; i < this.nodes.size(); i++)
			{
				this.nodes.get(i).setStatus(0);
			}
			
			try {
				Thread.sleep(restTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int checkForDeadLocks(GPNode<?> node)
	{
		/*check for cycles to identify deadlocks
		 * nodes status:
		 * 0 - unchecked
		 * 1 - the node is on a deadlock
		 * 2 - the is blocked but isn't on the deadlock cycle
		 * 3 - the is not blocked*/
		if(node.getStatus() == 0)
		{
			if(node.numberOfEdges() == 0)
			{
				node.setStatus(3);
				return 3;
			}
			else
			{
				if(this.nodesStack.contains(node))
				{
					node.setStatus(1);
					this.nodesStack.clear();
					this.nodesStack.push(node);
					return 1;
				}
				else
				{
					this.nodesStack.push(node);
					switch (checkForDeadLocks(node.getEdgeAt(0).getDestinationVertex()))
					{
						case 1:
							if(node.getStatus() == 0)
							{
								node.setStatus(1);
								this.nodesStack.push(node);
								return 1;
							}
							else
								return 2;
							
						case 2:
							node.setStatus(2);
							return 2;
						
						case 3:
							node.setStatus(3);
							return 3;
					}
				}
			}
		}
		else
		{
			if(node.getStatus() != 3)
				return 2;
			else
				return 3;
		}
		
		return 0;
	}
	
	@Override
	public void processWillClaimResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
		/*its called when is needed to draw a edge form the process to the resource*/
		
		try {
			semaphoreGraph.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OPEdge waitEdge = new OPEdge(resourceNode);
		graph.addEdgeToNode(waitEdge, processNode);
		
		if(delegate != null) {
			delegate.systemAppendedEdgeToNode(this, processNode);
		}
		
		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
		
		semaphoreGraph.release();
	}

	@Override
	public void processDidAcquireResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
		/*its called when is needed to draw a edge form the resource to the process*/
		
		try {
			semaphoreGraph.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
		
		semaphoreGraph.release();
	}

	@Override
	public void processNeedReleaseResource(OPProcessNode<OPProcess> processNode,
			OPResourceNode<OPResource> resourceNode) {
		/*when the resource is released, the edges are deleted*/
		
		try {
			semaphoreGraph.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(delegate != null) {
			delegate.systemWillRemoveLastEdgeFromNode(this, resourceNode);
		}
		
		graph.removeEdgeFromNode(resourceNode.getEdgeAt(resourceNode.numberOfEdges() - 1), resourceNode);
		

		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
		
		semaphoreGraph.release();
	}
	
	public void addProcess(OPProcess opProcess) {
		/*add a process to the graph and array, and set its delegate*/

		
	
		OPProcessNode<OPProcess> processNode = new OPProcessNode<OPProcess>(opProcess);
		opProcess.setSelfNode(processNode);
		
		processes.add(processNode);
		nodes.add(processNode);
		try {
			semaphoreGraph.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graph.addNode(processNode);
		
		semaphoreGraph.release();
		
		opProcess.setDelegate(this);
		
		new Thread(this).start();
	}
	
	public void addResource(OPResource opResource) {
		OPResourceNode<OPResource> resourceNode = new OPResourceNode<OPResource>(opResource);
		
		resources.add(resourceNode);
		nodes.add(resourceNode);
		graph.addNode(resourceNode);
	}

	public void addAllResources(List<OPResource> resources) {
		for (OPResource resource: resources) {
			addResource(resource);
		}
	}
	
	public Integer numberOfProcesses() {
		return this.processes.size();
	}
	
	public Integer numberOfResources() {
		return this.resources.size();
	}
	
	public OPProcessNode<OPProcess> getProcess(Integer index) {
		return this.processes.get(index);
	}
	
	public OPResourceNode<OPResource> getResource(Integer index) {
		return this.resources.get(index);
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
