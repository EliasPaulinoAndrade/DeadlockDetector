package graph;

import java.util.LinkedList;
import java.util.List;

public class Node<NodeElement, PathElement>{
	private Integer id;
	private NodeElement value;
	private List<Edge<NodeElement, PathElement>> edges;
	
	public Node(NodeElement value, Integer id) {
		this.value = value;
		this.id = id;
		this.edges = new LinkedList<>();
	}
	
	public Integer getId() {
		return id;
	}

	public NodeElement getValue() {
		return value;
	}
	
	public void setValue(NodeElement value) {
		this.value = value;
	}
	
	public Boolean addEdge(Edge<NodeElement, PathElement> edge) {
		return this.edges.add(edge);
	}
	
	public Boolean removeEdge(Edge<NodeElement, PathElement> edge) {
		return this.edges.remove(edge);
	}
	
	@Override
	public String toString() {
		StringBuilder nodeStringBuilder = new StringBuilder();
		
		nodeStringBuilder.append("[id: " + id);
		nodeStringBuilder.append(", value: " + value + "]");
		for(Edge<NodeElement, PathElement> edge : edges) {
			nodeStringBuilder.append("  ->  ");
			nodeStringBuilder.append(edge);
		}
		
		return nodeStringBuilder.toString();
	}
}
