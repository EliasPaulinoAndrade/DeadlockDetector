package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph{
	private List<Node> vertices;

	public Graph() {
		vertices = new LinkedList<>();
	}

	public List<Node> getVertices() {
		return vertices;
	}

	public void setVertices(List<Node> vertices) {
		this.vertices = vertices;
	}
}
