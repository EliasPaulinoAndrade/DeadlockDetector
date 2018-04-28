package graph;

public class MyEdge<PathElement> {
	private MyNode<?> destinationVertex;
	private PathElement pathInformation;
	
	public MyEdge(MyNode<?> node2, PathElement pathInformation) {
		this.destinationVertex = node2;
		this.pathInformation = pathInformation;
	}

	public MyNode<?> getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(MyNode<?> destinationVertex) {
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
