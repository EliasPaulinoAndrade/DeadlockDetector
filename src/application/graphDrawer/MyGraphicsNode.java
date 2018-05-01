package application.graphDrawer;

import java.util.Dictionary;
import java.util.Hashtable;

import javafx.scene.Node;

public class MyGraphicsNode {
	Node node;
	private Dictionary<Integer, MyGraphicsEdge> edges;
	public MyGraphicsNode(Node node) {
		this.node = node;
		this.edges = new Hashtable<>();
	}
	public void addToEdges(Integer index, MyGraphicsEdge edge) {
		this.edges.put(index, edge);
	}
	public MyGraphicsEdge removeFromEdges(Integer index) {
		return this.edges.remove(index);	
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
}
