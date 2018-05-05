package application.op_graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import application.delegate_definitions.OPProcessDelegate;
import graph.GPGraph;
import graph.GPNodeValue;
import javafx.application.Platform;

/* it is responsible for trying to allocate new resources from time to time*/

public class OPProcess implements GPNodeValue, Runnable{
	private String processIdentifier;
	private Integer restTime;
	private Integer activeTime;
	
	private OPProcessNode<OPProcess> selfNode;
	private List<ResourcesTime> usingResources;
	
	private Long lastClaimedTime;
	private Random random;
	
	private OPProcessDelegate delegate;
	
	public OPProcess(String processIdentifier, Integer restTime, Integer activeTime) {
		super();
		this.processIdentifier = processIdentifier;
		this.restTime = restTime;
		this.activeTime = activeTime;
		this.usingResources = new ArrayList<>();
		this.lastClaimedTime = System.currentTimeMillis();
		this.random = new Random();
	}
	
	private Boolean checkResourceHasBeenUsed(OPResourceNode<OPResource> resourceNode) {
		for(ResourcesTime resourcesTime: this.usingResources) {
			if(resourcesTime.resource == resourceNode) {
				return true;
			}
		}
		return false;
	}
	private OPResourceNode<OPResource> chooseAResource() {
		List<OPResourceNode<OPResource>> unusedResourceNode = new ArrayList<>();
		
		for(OPResourceNode<OPResource> resourceNode : OPSystem.shared().resourcesIterable) {
			if(!checkResourceHasBeenUsed(resourceNode)) {
				unusedResourceNode.add(resourceNode);
			}
		}
		
		if(unusedResourceNode.size() == 0) {
			return null;
		}
		
		Integer randomResourceIndex = random.nextInt(unusedResourceNode.size());
		OPResourceNode<OPResource> randomResourceNode = unusedResourceNode.get(randomResourceIndex);
		
		return randomResourceNode;
	}
	
	private void tryToClaimRandomResource(Long currentTime) throws InterruptedException {
		/*try claim a random resource, it can wait by it, or continue if the resource is free.*/
		
		if(currentTime - this.lastClaimedTime < this.restTime) {
			return ;
		}
		
		OPResourceNode<OPResource> randomResourceNode = chooseAResource();
		
		System.out.println(this.usingResources.size());
		
		if(randomResourceNode == null) {
			this.lastClaimedTime = System.currentTimeMillis();
			return ;
		}
		
		GPGraph graph = OPSystem.shared().getGraph();
		OPResource randomResource = randomResourceNode.getValue();
		
		OPEdge waitEdge = new OPEdge(randomResourceNode);
		graph.addEdgeToNode(waitEdge, selfNode);
		
		Integer lastEdge2 = selfNode.numberOfEdges() - 1;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					OPSystem.shared().getDrawer().addEdgeToNodeAt(selfNode.getId(), lastEdge2);
				}catch (Exception e) {
					System.out.println("deu ruim");
				}
			}
		});
		

		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
		
		randomResource.claim();
		this.lastClaimedTime = System.currentTimeMillis();
		
		this.usingResources.add(
				new ResourcesTime(
						randomResourceNode, 
						this.lastClaimedTime
						)
				);
		
		Integer lastNode = selfNode.numberOfEdges() - 1;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					OPSystem.shared().getDrawer().removeEdgeFromNodeAt(selfNode.getId(), lastNode);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		graph.removeEdgeFromNode(selfNode.getEdgeAt(selfNode.numberOfEdges() - 1), selfNode);
		
		OPEdge claimedEdge = new OPEdge(selfNode);
		graph.addEdgeToNode(claimedEdge, randomResourceNode);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				OPSystem.shared().getDrawer().addEdgeToNodeAt(randomResourceNode.getId(), randomResourceNode.numberOfEdges() - 1);
				
			}
		});
		
		System.out.println(OPSystem.shared().getGraph());
		System.out.println("-----------------------------");
	}

	private void tryToFreeResources(Long currentTime) {
		/*it frees the finished resources*/
		
		List<ResourcesTime> finishedResources = new ArrayList<>();
		for(ResourcesTime resourcesTime : this.usingResources) {
			if(currentTime - resourcesTime.initialTime > this.activeTime) {

				System.out.println("will free");
				Integer edgeIndex = resourcesTime.resource.numberOfEdges() - 1;
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						OPSystem.shared().getDrawer().removeEdgeFromNodeAt(resourcesTime.resource.getId(), edgeIndex);
						
					}
				});
				OPSystem.shared().getGraph().removeEdgeFromNode(resourcesTime.resource.getEdgeAt(resourcesTime.resource.numberOfEdges() - 1), resourcesTime.resource);
				resourcesTime.resource.getValue().release();
				finishedResources.add(resourcesTime);
			}
		}
		this.usingResources.removeAll(finishedResources);
	}
	
	
	@Override
	public void run() {
		Long currentTime;
		while(true) {
			try {
				currentTime = System.currentTimeMillis();
				tryToFreeResources(currentTime);
				
				tryToClaimRandomResource(currentTime);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public String getStringValue() {
		return processIdentifier.toString();
	}
	
	public String getProcessIdentifier() {
		return processIdentifier;
	}

	public void setProcessIdentifier(String processIdentifier) {
		this.processIdentifier = processIdentifier;
	}

	public Integer getRestTime() {
		return restTime;
	}

	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}

	public Integer getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Integer activeTime) {
		this.activeTime = activeTime;
	}

	public OPProcessNode<OPProcess> getSelfNode() {
		return selfNode;
	}

	public void setSelfNode(OPProcessNode<OPProcess> selfNode) {
		this.selfNode = selfNode;
	}
	
	public OPProcessDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(OPProcessDelegate delegate) {
		this.delegate = delegate;
	}

	static class ResourcesTime {
		protected OPResourceNode<OPResource> resource;
		protected Long initialTime;
		public ResourcesTime(OPResourceNode<OPResource> resource, Long initialTime) {
			super();
			this.resource = resource;
			this.initialTime = initialTime;
		}
	}
}
