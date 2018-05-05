package application.op_graph;

import graph.GPEdge;
import graph.GPNode;

public class OPEdge extends GPEdge<Void>{
	
	public OPEdge(OPResourceNode<OPResource> destinationVertex) {
		super(destinationVertex, null);
	}
	
	public OPEdge(OPProcessNode<OPProcess> destinationVertex) {
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
