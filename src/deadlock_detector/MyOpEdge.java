package deadlock_detector;

import graph.GPEdge;
import graph.GPNode;

public class MyOpEdge extends GPEdge<Void>{
	
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
