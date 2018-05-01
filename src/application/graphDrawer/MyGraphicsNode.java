package application.graphDrawer;

import java.util.Dictionary;
import java.util.Hashtable;

import application.graphDrawer.eventHandlers.MyNodeEventHandler;
import javafx.scene.Node;

/*its a graphical representation of a node, it have a node and the node index in the graph.
 * this class encapsulates the nodes listeners, and set itself to the eventhandler, in this way, 
 * the handle() method has access to the node and it index 
 * */
public class MyGraphicsNode {
	private Node node;
	private Integer index;
	private Dictionary<Integer, MyGraphicsEdge> startingEdges;
	private Dictionary<Integer, MyGraphicsEdge> endingEdges;
	
	
	public MyGraphicsNode(Node node, Integer index) {
		this.node = node;
		this.index = index;
		this.startingEdges = new Hashtable<>();
		this.endingEdges = new Hashtable<>();
	}
	
	public Dictionary<Integer, MyGraphicsEdge> getEndingEdges() {
		return endingEdges;
	}

	public void setEndingEdges(Dictionary<Integer, MyGraphicsEdge> endingEdges) {
		this.endingEdges = endingEdges;
	}

	public Dictionary<Integer, MyGraphicsEdge> getStartingEdges() {
		return startingEdges;
	}

	public void setStartingEdges(Dictionary<Integer, MyGraphicsEdge> edges) {
		this.startingEdges = edges;
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
		node.setOnMouseDragged(eventHandler);
	}
	
	public void setOnNodeMouseDown(MyNodeEventHandler eventHandler) {
		node.setOnMousePressed(eventHandler);
	}
	
	public void setOnNodeMouseUp(MyNodeEventHandler eventHandler) {
		node.setOnMouseReleased(eventHandler);	
	}
}
