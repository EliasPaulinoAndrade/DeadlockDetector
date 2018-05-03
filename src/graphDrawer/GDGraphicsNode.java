package graphDrawer;

import java.util.Dictionary;
import java.util.Hashtable;

import graphDrawer.eventHandlers.GDNodeEventHandler;
import javafx.scene.Node;

/*its a graphical representation of a node, it have a node and the node index in the graph.
 * this class encapsulates the nodes listeners, and set itself to the eventhandler, in this way, 
 * the handle() method has access to the node and it index 
 * */
public class GDGraphicsNode {
	private Node node;
	private Integer index;
	private Dictionary<Integer, GDGraphicsEdge> startingEdges;
	private Dictionary<Integer, GDGraphicsEdge> endingEdges;
	
	
	public GDGraphicsNode(Node node, Integer index) {
		this.node = node;
		this.index = index;
		this.startingEdges = new Hashtable<>();
		this.endingEdges = new Hashtable<>();
	}
	
	public Dictionary<Integer, GDGraphicsEdge> getEndingEdges() {
		return endingEdges;
	}

	public void setEndingEdges(Dictionary<Integer, GDGraphicsEdge> endingEdges) {
		this.endingEdges = endingEdges;
	}

	public Dictionary<Integer, GDGraphicsEdge> getStartingEdges() {
		return startingEdges;
	}

	public void setStartingEdges(Dictionary<Integer, GDGraphicsEdge> edges) {
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

	public void setOnNodeDragged(GDNodeEventHandler eventHandler) {
		node.setOnMouseDragged(eventHandler);
	}
	
	public void setOnNodeMouseDown(GDNodeEventHandler eventHandler) {
		node.setOnMousePressed(eventHandler);
	}
	
	public void setOnNodeMouseUp(GDNodeEventHandler eventHandler) {
		node.setOnMouseReleased(eventHandler);	
	}
}
