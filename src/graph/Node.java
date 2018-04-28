package graph;

import java.util.LinkedList;
import java.util.List;

public class Node<Element>{
	private Element value;
	private List<Edge> edges;
	public Node(Element value) {
		this.value = value;
		this.edges = new LinkedList<>();
	}
	public Element getValue() {
		return value;
	}
	public void setValue(Element value) {
		this.value = value;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
}
