package graph;

import java.util.LinkedList;
import java.util.List;

public class Graph<NodeElement, PathElement>{
	private List<Node<NodeElement, PathElement>> vertices;

	public Graph() {
		vertices = new LinkedList<>();
	}

	public List<Node<NodeElement, PathElement>> getVertices() {
		return vertices;
	}

	public void setVertices(List<Node<NodeElement, PathElement>> vertices) {
		this.vertices = vertices;
	}
}
