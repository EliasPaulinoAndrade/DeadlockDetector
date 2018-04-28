package graph;

import java.util.LinkedList;
import java.util.List;

public class MyGraph<NodeElement, PathElement>{
	private List<MyNode<NodeElement, PathElement>> vertices;

	public MyGraph() {
		vertices = new LinkedList<>();
	}

	public Boolean addNode(MyNode<NodeElement, PathElement> node) {
		return this.vertices.add(node);
	}
	
	public Boolean removeNode(MyNode<NodeElement, PathElement> node) {
		return this.vertices.remove(node);
	}

	@Override
	public String toString() {
		StringBuilder graphStringBuilder = new StringBuilder();
		
		for(MyNode<NodeElement, PathElement> node : vertices) {
			graphStringBuilder.append(node);
			graphStringBuilder.append("\n");
		}
		return graphStringBuilder.toString();
	}
}
