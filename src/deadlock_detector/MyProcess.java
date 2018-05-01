package deadlock_detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph.MyGraph;
import graph.MyNodeValue;
import javafx.application.Platform;

/* it is responsible for trying to allocate new resources from time to time*/

public class MyProcess implements MyNodeValue, Runnable{
	private String processIdentifier;
	private Double restTime;
	private Double activeTime;
	
	private MyOpSystem opSystem;
	private MyProcessNode<MyProcess> selfNode;
	private List<ResourcesTime> usingResources;
	
	private Long lastClaimedTime;
	
	private Random random;
	
	public MyProcess(String processIdentifier, Double restTime, Double activeTime, MyOpSystem opSystem) {
		super();
		this.processIdentifier = processIdentifier;
		this.restTime = restTime;
		this.activeTime = activeTime;
		this.opSystem = opSystem;
		this.usingResources = new ArrayList<>();
		this.lastClaimedTime = System.currentTimeMillis();
		this.random = new Random();
	}
	
	private Boolean checkResourceHasBeenUsed(MyResourceNode<MyResource> resourceNode) {
		for(ResourcesTime resourcesTime: this.usingResources) {
			if(resourcesTime.resource == resourceNode) {
				return true;
			}
		}
		return false;
	}
	private MyResourceNode<MyResource> chooseAResource() {
		List<MyResourceNode<MyResource>> resourcesNode = opSystem.getResources();

		List<MyResourceNode<MyResource>> unusedResourceNode = new ArrayList<>();
		
		for(MyResourceNode<MyResource> resourceNode : resourcesNode) {
			if(!checkResourceHasBeenUsed(resourceNode)) {
				unusedResourceNode.add(resourceNode);
			}
		}
		
		if(unusedResourceNode.size() == 0) {
			return null;
		}
		
		Integer randomResourceIndex = random.nextInt(unusedResourceNode.size());
		MyResourceNode<MyResource> randomResourceNode = unusedResourceNode.get(randomResourceIndex);
		
		return randomResourceNode;
	}
	private void tryToClaimRandomResource(Long currentTime) throws InterruptedException {
		/*try claim a random resource, it can wait by it, or continue if the resource is free.*/
		
		if(currentTime - this.lastClaimedTime < this.restTime) {
			return ;
		}
		
		MyResourceNode<MyResource> randomResourceNode = chooseAResource();
		
		if(randomResourceNode == null) {
			this.lastClaimedTime = System.currentTimeMillis();
			return ;
		}
		
		MyGraph graph = opSystem.getGraph();
		MyResource randomResource = randomResourceNode.getValue();
		
		System.out.println(randomResource.getResourceIdentifier() + " ->  " + selfNode.getValue().getProcessIdentifier());
		
		MyOpEdge waitEdge = new MyOpEdge(randomResourceNode);
		graph.addEdgeToNode(waitEdge, selfNode);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				opSystem.getDrawer().addEdgeToNodeAt(selfNode.getId(), selfNode.numberOfEdges() - 1);
				
			}
		});
		
		randomResource.claim();
		this.lastClaimedTime = System.currentTimeMillis();
		
		this.usingResources.add(
				new ResourcesTime(
						randomResourceNode, 
						this.lastClaimedTime
						)
				);
		
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				opSystem.getDrawer().removeEdgeFromNodeAt(selfNode.getId(), selfNode.numberOfEdges() - 1);
				
			}
		});
		
		MyOpEdge claimedEdge = new MyOpEdge(selfNode);
		graph.addEdgeToNode(claimedEdge, randomResourceNode);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				opSystem.getDrawer().addEdgeToNodeAt(randomResourceNode.getId(), randomResourceNode.numberOfEdges() - 1);
				
			}
		});
		
	}

	private void tryToFreeResources(Long currentTime) {
		/*it frees the finished resources*/
		
		List<ResourcesTime> finishedResources = new ArrayList<>();
		for(ResourcesTime resourcesTime : this.usingResources) {
			if(currentTime - resourcesTime.initialTime > this.activeTime) {
				Integer edgeIndex = resourcesTime.resource.numberOfEdges() - 1;
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						opSystem.getDrawer().removeEdgeFromNodeAt(resourcesTime.resource.getId(), edgeIndex);
						
					}
				});
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

	public MyProcessNode<MyProcess> getSelfNode() {
		return selfNode;
	}

	public void setSelfNode(MyProcessNode<MyProcess> selfNode) {
		this.selfNode = selfNode;
	}
	
	static class ResourcesTime {
		protected MyResourceNode<MyResource> resource;
		protected Long initialTime;
		public ResourcesTime(MyResourceNode<MyResource> resource, Long initialTime) {
			super();
			this.resource = resource;
			this.initialTime = initialTime;
		}
	}
}
