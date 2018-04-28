package graph;

import java.util.LinkedList;
import java.util.List;

public class MyNode<NodeElement extends MyNodeValue>{
	private Integer id;
	private NodeElement value;
	private List<MyEdge<?>> edges;
	
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
	
	protected Boolean addEdge(MyEdge<?> edge) {
		return this.edges.add(edge);
	}
	
	protected Boolean removeEdge(MyEdge<?> edge) {
		return this.edges.remove(edge);
	}
	
	public MyEdge<?> getEdgeAt(Integer index) {
		return this.edges.get(index);
	}
	
	public Integer numberOfEdges() {
		return this.edges.size();
	}
	
	@Override
	public String toString() {
		StringBuilder nodeStringBuilder = new StringBuilder();
		
		nodeStringBuilder.append("[id: " + id);
		nodeStringBuilder.append(", value: " + value.getStringValue() + "]");
		for(MyEdge<?> edge : edges) {
			nodeStringBuilder.append("  ->  ");
			nodeStringBuilder.append(edge);
		}
		
		return nodeStringBuilder.toString();
	}
}
