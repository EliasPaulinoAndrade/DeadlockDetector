package graph;

import java.util.LinkedList;
import java.util.List;

public class MyNode<NodeElement, PathElement>{
	private Integer id;
	private NodeElement value;
	private List<MyEdge<NodeElement, PathElement>> edges;
	
	public MyNode(NodeElement value, Integer id) {
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
	
	public Boolean addEdge(MyEdge<NodeElement, PathElement> edge) {
		return this.edges.add(edge);
	}
	
	public Boolean removeEdge(MyEdge<NodeElement, PathElement> edge) {
		return this.edges.remove(edge);
	}
	
	@Override
	public String toString() {
		StringBuilder nodeStringBuilder = new StringBuilder();
		
		nodeStringBuilder.append("[id: " + id);
		nodeStringBuilder.append(", value: " + value + "]");
		for(MyEdge<NodeElement, PathElement> edge : edges) {
			nodeStringBuilder.append("  ->  ");
			nodeStringBuilder.append(edge);
		}
		
		return nodeStringBuilder.toString();
	}
}
