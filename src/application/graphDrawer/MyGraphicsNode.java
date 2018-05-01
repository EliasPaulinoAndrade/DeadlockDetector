package application.graphDrawer;

import java.util.Dictionary;
import java.util.Hashtable;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class MyGraphicsNode {
	private Node node;
	private Integer index;
	private Dictionary<Integer, MyGraphicsEdge> edges;
	
	public MyGraphicsNode(Node node, Integer index) {
		this.node = node;
		this.index = index;
		this.edges = new Hashtable<>();
	}
	
	public Dictionary<Integer, MyGraphicsEdge> getEdges() {
		return edges;
	}

	public void setEdges(Dictionary<Integer, MyGraphicsEdge> edges) {
		this.edges = edges;
	}

	public Node getNode() {
		return node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setOnNodeDragged(MyNodeEventHandler eventHandler) {
		eventHandler.setGraphicsNode(this);
		node.setOnMouseDragged(eventHandler);
	}
	
	public void setOnNodeMouseDown(MyNodeEventHandler eventHandler) {
		eventHandler.setGraphicsNode(this);
		node.setOnMousePressed(eventHandler);
	}
	
	public void setOnNodeMouseUp(MyNodeEventHandler eventHandler) {
		eventHandler.setGraphicsNode(this);
		node.setOnMouseReleased(eventHandler);	
	}
}
