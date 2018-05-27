package application.op_graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.op_graph.delegates.OPProcessDelegate;
import graph.GPNodeValue;

/* it is responsible for trying to allocate new resources from time to time*/

public class OPProcess implements GPNodeValue, Runnable{
	private String processIdentifier;
	private Integer restTime;
	private Integer activeTime;
	private Integer willDie;

	private OPProcessNode<OPProcess> selfNode;
	private List<ResourcesTime> usingResources;
	private OPResource claimedResource;
	
	private Long lastClaimedTime;
	private Random random;
	
	private OPProcessDelegate delegate;
	
	public OPProcess(String processIdentifier, Integer restTime, Integer activeTime) {
		super();
		this.processIdentifier = processIdentifier;
		this.restTime = restTime;
		this.activeTime = activeTime;
		this.willDie = 0;
		this.usingResources = new ArrayList<>();
		this.lastClaimedTime = System.currentTimeMillis();
		this.random = new Random();
		
	}
	
	@Override
	public void run() {
		Long currentTime;
		while(this.getWillDie() == 0) {
			try {
				currentTime = System.currentTimeMillis();
				tryToFreeResources(currentTime);
				
				tryToClaimRandomResource(currentTime);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		freeResourcesBeforeDie();
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
		if(randomResourceNode == null) {
			this.lastClaimedTime = System.currentTimeMillis();
			return ;
		}
		
		OPResource randomResource = randomResourceNode.getValue();

		if(delegate != null) {
			delegate.processWillClaimResource(selfNode, randomResourceNode);		
		}
		
		this.setClaimedResource(randomResource);
		
		randomResource.claim();
		
		if(this.getWillDie() == 1)
		{
			randomResource.release();
			this.setClaimedResource(null);
		}
		
		this.lastClaimedTime = System.currentTimeMillis();
		this.usingResources.add(
				new ResourcesTime(
						randomResourceNode, 
						this.lastClaimedTime
						)
				);
		
		if(delegate != null) {
			delegate.processDidAcquireResource(selfNode, randomResourceNode);
		}
	}

	private void tryToFreeResources(Long currentTime) {
		/*it frees the finished resources*/
		
		List<ResourcesTime> finishedResources = new ArrayList<>();
		for(ResourcesTime resourcesTime : this.usingResources) {
			if(currentTime - resourcesTime.initialTime > this.activeTime) {
				
				if(delegate != null) {
					delegate.processNeedReleaseResource(selfNode, resourcesTime.resource);
				}
				
				resourcesTime.resource.getValue().release();
				finishedResources.add(resourcesTime);
			}
		}
		this.usingResources.removeAll(finishedResources);
	}
	
	private void freeResourcesBeforeDie()
	{
		if(this.getUsingResources().size() != 0)
		{
			for(ResourcesTime resource : this.getUsingResources())
			{
				if(delegate != null) {
					delegate.processNeedReleaseResource(selfNode, resource.resource);
				}
				resource.resource.getValue().release();
			}
			
			this.getUsingResources().clear();
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

	public Integer getWillDie() {
		return willDie;
	}

	public void setWillDie(Integer willDie) {
		this.willDie = willDie;
	}

	public OPResource getClaimedResource() {
		return claimedResource;
	}

	public void setClaimedResource(OPResource claimedResource) {
		this.claimedResource = claimedResource;
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

	public List<ResourcesTime> getUsingResources() {
		return usingResources;
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
