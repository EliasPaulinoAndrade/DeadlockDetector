package graph;

import java.util.LinkedList;
import java.util.List;


/*graph's class, it has a linked list for do the 'adjacency list' implementation */
public class GPGraph{
	private List<GPNode<?>> vertices;

	public GPGraph() {
		vertices = new LinkedList<>();
	}
	
	public Boolean addEdgeToNode(GPEdge<?> edge, GPNode<?> node) {
		GPNode<?> destinationNode = edge.getDestinationVertex();
		if(vertices.contains(destinationNode)){
			return node.addEdge(edge);
		}
		return false;
	}
	
	public Boolean removeEdgeFromNode(GPEdge<?> edge, GPNode<?> node) {
		return node.removeEdge(edge);
	}

	public Boolean addNode(GPNode<?> node) {
		node.setId(this.vertices.size());
		return this.vertices.add(node);
	}
	
	public Boolean removeNode(GPNode<?> node) {
		return this.vertices.remove(node);
	}
	
	public Integer numberOfNodes() {
		return this.vertices.size();
	}
	
	public GPNode<?> getNodeAt(Integer index){
		return vertices.get(index);
	}

	@Override
	public String toString() {
		StringBuilder graphStringBuilder = new StringBuilder();
		
		for(GPNode<?> node : vertices) {
			graphStringBuilder.append(node);
			graphStringBuilder.append("\n");
		}
		return graphStringBuilder.toString();
	}
}
