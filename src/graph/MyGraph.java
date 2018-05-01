package graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/*graph's class, it have a linked list for do the 'adjacency list' implementation */
public class MyGraph{
	private List<MyNode<?>> vertices;

	public MyGraph() {
		vertices = new LinkedList<>();
	}
	
	public Boolean addEdgeToNode(MyEdge<?> edge, MyNode<?> node) {
		MyNode<?> destinationNode = edge.getDestinationVertex();
		if(vertices.contains(destinationNode)){
			return node.addEdge(edge);
		}
		return false;
	}
	
	public Boolean removeEdgeFromNode(MyEdge<?> edge, MyNode<?> node) {
		return node.removeEdge(edge);
	}

	public Boolean addNode(MyNode<?> node) {
		node.setId(this.vertices.size());
		return this.vertices.add(node);
	}
	
	public Boolean removeNode(MyNode<?> node) {
		return this.vertices.remove(node);
	}
	
	public Integer numberOfNodes() {
		return this.vertices.size();
	}
	
	public MyNode<?> getNodeAt(Integer index){
		return vertices.get(index);
	}

	@Override
	public String toString() {
		StringBuilder graphStringBuilder = new StringBuilder();
		
		for(MyNode<?> node : vertices) {
			graphStringBuilder.append(node);
			graphStringBuilder.append("\n");
		}
		return graphStringBuilder.toString();
	}
}
