package deadlock_detector;

import graph.MyEdge;
import graph.MyNode;

public class MyOpEdge extends MyEdge<Void>{
	
	public MyOpEdge(MyResourceNode<MyResource> destinationVertex) {
		super(destinationVertex, null);
	}
	
	public MyOpEdge(MyProcessNode<MyProcess> destinationVertex) {
		super(destinationVertex, null);
	}
	
	@Override
	public String toString() {
		StringBuilder edgeStringBuilder = new StringBuilder();
		
		edgeStringBuilder.append("[(to_node_id: ");
		edgeStringBuilder.append(getDestinationVertex().getId() + ")]");
		
		return edgeStringBuilder.toString();
		
	}
}
