package graph;

import java.util.LinkedList;
import java.util.List;

/*each node has a linked list for show it paths to other nodes*/
public class GPNode<NodeElement extends GPNodeValue>{
	private Integer id;
	private NodeElement value;
	private List<GPEdge<?>> edges;
	private Integer status;
	
	public GPNode(NodeElement value) {
		this.value = value;
		this.edges = new LinkedList<>();
		this.status = 0;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public NodeElement getValue() {
		return value;
	}
	
	public void setValue(NodeElement value) {
		this.value = value;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	protected Boolean addEdge(GPEdge<?> edge) {
		return this.edges.add(edge);
	}
	
	protected Boolean removeEdge(GPEdge<?> edge) {
		return this.edges.remove(edge);
	}
	
	public GPEdge<?> getEdgeAt(Integer index) {
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
		for(GPEdge<?> edge : edges) {
			nodeStringBuilder.append("  ->  ");
			nodeStringBuilder.append(edge);
		}
		
		return nodeStringBuilder.toString();
	}
}
