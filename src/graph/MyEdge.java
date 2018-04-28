package graph;

public class MyEdge<NodeElement, PathElement> {
	private MyNode<NodeElement, PathElement> destinationVertex;
	private PathElement pathInformation;
	
	public MyEdge(MyNode<NodeElement, PathElement> destinationVertex, PathElement pathInformation) {
		this.destinationVertex = destinationVertex;
		this.pathInformation = pathInformation;
	}

	public MyNode<NodeElement, PathElement> getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(MyNode<NodeElement, PathElement> destinationVertex) {
		this.destinationVertex = destinationVertex;
	}

	public PathElement getPathInformation() {
		return pathInformation;
	}

	public void setPathInformation(PathElement pathInformation) {
		this.pathInformation = pathInformation;
	}
	
	@Override
	public String toString() {
		StringBuilder edgeStringBuilder = new StringBuilder();
		
		edgeStringBuilder.append("[(path_info: " + pathInformation);
		edgeStringBuilder.append(") => (to_node_id: ");
		edgeStringBuilder.append(destinationVertex.getId() + ")]");
		
		return edgeStringBuilder.toString();
		
	}
}
