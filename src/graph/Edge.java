package graph;

public class Edge<Element> {
	private Node destinationVertex;
	private Element pathInformation;
	
	public Edge(Node destinationVertex, Element pathInformation) {
		this.destinationVertex = destinationVertex;
		this.pathInformation = pathInformation;
	}

	public Node getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(Node destinationVertex) {
		this.destinationVertex = destinationVertex;
	}

	public Element getPathInformation() {
		return pathInformation;
	}

	public void setPathInformation(Element pathInformation) {
		this.pathInformation = pathInformation;
	}
	
}
