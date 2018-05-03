package graph;

/*each edge has the a destination node and a path information, like: 'distance between houses', 'time' and etc*/
public class GPEdge<PathElement> {
	private GPNode<?> destinationVertex;
	private PathElement pathInformation;
	
	public GPEdge(GPNode<?> destinationVertex, PathElement pathInformation) {
		this.destinationVertex = destinationVertex;
		this.pathInformation = pathInformation;
	}

	public GPNode<?> getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(GPNode<?> destinationVertex) {
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
