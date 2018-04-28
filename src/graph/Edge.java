package graph;

public class Edge<NodeElement, PathElement> {
	private Node<NodeElement, PathElement> destinationVertex;
	private PathElement pathInformation;
	
	public Edge(Node<NodeElement, PathElement> destinationVertex, PathElement pathInformation) {
		this.destinationVertex = destinationVertex;
		this.pathInformation = pathInformation;
	}

	public Node<NodeElement, PathElement> getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(Node<NodeElement, PathElement> destinationVertex) {
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
