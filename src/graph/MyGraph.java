package graph;

import java.util.LinkedList;
import java.util.List;

public class MyGraph{
	private List<MyNode<?>> vertices;

	public MyGraph() {
		vertices = new LinkedList<>();
	}

	public Boolean addNode(MyNode<?> node) {
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
