package graph;

import java.util.LinkedList;
import java.util.List;

public class Graph<NodeElement, PathElement>{
	private List<Node<NodeElement, PathElement>> vertices;

	public Graph() {
		vertices = new LinkedList<>();
	}

	public Boolean addNode(Node<NodeElement, PathElement> node) {
		return this.vertices.add(node);
	}
	
	public Boolean removeNode(Node<NodeElement, PathElement> node) {
		return this.vertices.remove(node);
	}

	@Override
	public String toString() {
		StringBuilder graphStringBuilder = new StringBuilder();
		
		for(Node<NodeElement, PathElement> node : vertices) {
			graphStringBuilder.append(node);
			graphStringBuilder.append("\n");
		}
		return graphStringBuilder.toString();
	}
}
