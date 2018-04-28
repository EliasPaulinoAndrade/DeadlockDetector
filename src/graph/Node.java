package graph;

import java.util.LinkedList;
import java.util.List;

public class Node<NodeElement, PathElement>{
	private NodeElement value;
	private List<Edge<NodeElement, PathElement>> edges;
	
	public Node(NodeElement value) {
		this.value = value;
		this.edges = new LinkedList<>();
	}
	
	public NodeElement getValue() {
		return value;
	}
	
	public void setValue(NodeElement value) {
		this.value = value;
	}
	
	public List<Edge<NodeElement, PathElement>> getEdges() {
		return edges;
	}
	
	public void setEdges(List<Edge<NodeElement, PathElement>> edges) {
		this.edges = edges;
	}
}
