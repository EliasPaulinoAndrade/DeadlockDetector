package application.graphDrawer;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import application.graphDrawer.eventHandlers.MyNodeClickDownHandler;
import application.graphDrawer.eventHandlers.MyNodeClickUpHandler;
import application.graphDrawer.eventHandlers.MyNodeDragHandler;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


/*its the area where the graph is drawed
 * 
 * there are a datasource and delegate, they delegate the necessity to know things about the particular graph implementations, how: number of nodes,
 * number of edges, color, set the nodes views, do something when a node is cliked and etc. 
 * 
 * this class use the delegate and datasource to draw the graph with the user especifications caring
 * */

public class MyGraphDrawer extends Region{
	private MyGraphDrawerDataSource dataSource;
	private MyGraphDrawerDelegate delegate;
	
	private Dictionary<Integer, MyGraphicsNode> nodes;
	
	public MyGraphDrawer() {
		super();
		this.nodes = new Hashtable<>();
		
	}

	public MyGraphDrawerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(MyGraphDrawerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public MyGraphDrawerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(MyGraphDrawerDelegate delegate) {
		this.delegate = delegate;
	}

	public void drawGraph() {
		/*draw all the nodes and edges*/
		
		if(dataSource == null) {
			return ;
		}
		
		Color rootColor = dataSource.graphDrawerGraphColor(this);
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		
		if(rootColor == null) {
			rootColor = MyGraphDrawerDefaultValues.graphDrawerGraphColor;
		}
		
		if(rootSize == null) {
			rootSize = MyGraphDrawerDefaultValues.graphDrawerGraphSize;
		}
		
		Background rootBackground = new Background(new BackgroundFill(rootColor, null, null));
	
		this.setMinSize(rootSize.getWidth(), rootSize.getHeight());
		
		this.setBackground(rootBackground);
		
		drawNodes();
		drawEdges();
	}
	
	public void addNodeAt(Integer index) {
		/*draw a node at a index
		 * */
		
		if(dataSource == null) {
			return;
		}
		
		Node node = dataSource.graphDrawerNodeViewForNodeAtIndex(this, index);
		MySize containerSize = dataSource.graphDrawerNodeMaxSize(this);
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		
		if(node == null) {
			return ;
		}
		if(containerSize == null) {
			containerSize = MyGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
		}
		
		if(this.getWidth()!=0 && this.getHeight()!=0) {
			rootSize = new MySize(this.getWidth(), this.getHeight());
		}
		else if(rootSize == null) {
			rootSize = MyGraphDrawerDefaultValues.graphDrawerGraphSize;
		}
		
		
		Pane containerNode = new StackPane();
		MyGraphicsNode graphicsNode = new MyGraphicsNode(containerNode, index);

		containerNode.setMinHeight(containerSize.getHeight());
		containerNode.setMinWidth(containerSize.getWidth());

		containerNode.getChildren().add(node);
		setNodeInRandomUnusedPoint(rootSize, containerNode);
		
		this.nodes.put(index, graphicsNode);
		this.getChildren().add(containerNode);	
		
		this.addNodeListeners(graphicsNode);
	}
	
	private void addNodeListeners(MyGraphicsNode graphicsNode) {
		/*check if the nodes can move, and set they listeners*/
		
		if(dataSource == null) {
			return ;
		}
		
		Boolean nodesCanMove = dataSource.graphDrawerNodesCanMove(this);
		if(nodesCanMove == null) {
			nodesCanMove = MyGraphDrawerDefaultValues.graphDrawerNodesCanMove;
		}
		
		if(dataSource.graphDrawerNodesCanMove(this)) {
			graphicsNode.setOnNodeMouseDown(new MyNodeClickDownHandler(graphicsNode, this));
			graphicsNode.setOnNodeDragged(new MyNodeDragHandler(graphicsNode, this));
			graphicsNode.setOnNodeMouseUp(new MyNodeClickUpHandler(graphicsNode, this));
		}
	}
	
	public void removeEdgeFromNodeAt(Integer nodeIndex, Integer edgeIndex) {
		/*remove a edge line from a node */
		
		MyGraphicsNode startGraphicsNode = this.nodes.get(nodeIndex);
		
		
		this.getChildren().remove(startGraphicsNode.getStartingEdges().get(edgeIndex).getNode());
		startGraphicsNode.getStartingEdges().remove(edgeIndex);
	}
	public void addEdgeToNodeAt(Integer nodeIndex, Integer edgeIndex){
		/*add a edge line from a node to it destination node*/
		
		if(dataSource == null) {
			return;
		}
		
		Integer destinationNodeIndex = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, edgeIndex, nodeIndex);
		if(destinationNodeIndex == null) {
			return;
		}
		
		Color edgeColor = dataSource.graphDrawerEdgesColor(this);
		Double edgeWidth = dataSource.graphDrawerEdgeStrokeWidth(this);
		
		if(edgeColor == null) {
			edgeColor = MyGraphDrawerDefaultValues.graphDrawerEdgesColor;
		}
		if(edgeWidth == null) {
			edgeWidth = MyGraphDrawerDefaultValues.graphDrawerEdgeStrokeWidth;
		}
		
		MyGraphicsNode startGraphicsNode = this.nodes.get(nodeIndex);
		Node startNode = startGraphicsNode.getNode();
		
		MyGraphicsNode endGraphicsNode = this.nodes.get(destinationNodeIndex);
		Node destinationNode = endGraphicsNode.getNode();
		
		MyGraphLine currentLineEdge = new MyGraphLine(startNode, destinationNode);
		currentLineEdge.setStroke(edgeColor);
		currentLineEdge.setStrokeWidth(edgeWidth);
		
		MyGraphicsEdge graphicsEdge = new MyGraphicsEdge(currentLineEdge, startGraphicsNode, endGraphicsNode);
		
		this.getChildren().add(currentLineEdge);
		startGraphicsNode.getStartingEdges().put(edgeIndex, graphicsEdge);
		endGraphicsNode.getEndingEdges().put(edgeIndex, graphicsEdge);
		
		currentLineEdge.toBack();
	}
	
	public void updateEdgeStartingInNodeAt(Integer nodeIndex, Integer edgeIndex) {
		/*it moves the line of a edge starting in a given node
		 * */
		
		Integer destinationNodeIndex = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, edgeIndex, nodeIndex);
		
		if(destinationNodeIndex == null) {
			return;
		}
		
		Dictionary<Integer, MyGraphicsEdge> nodeEdges = this.nodes.get(nodeIndex).getStartingEdges();

		Node node = this.nodes.get(nodeIndex).getNode();
		Node destinationNode = this.nodes.get(destinationNodeIndex).getNode();
		MyGraphLine edgeLine = nodeEdges.get(edgeIndex).getNode();
	
		edgeLine.setLinePosition(node, destinationNode);	
	}
	
	public void updateEdgeEndingInNodeAt(Integer destinationNodeIndex, Integer edgeIndex) {
		/*it moves the line of the edge ending in a given node*/
		
		/*Integer destinationNodeIndex = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, edgeIndex, nodeIndex);
		
		if(destinationNodeIndex == null) {
			return;
		}*/
		
		Dictionary<Integer, MyGraphicsEdge> nodeEdges = this.nodes.get(destinationNodeIndex).getEndingEdges();
		MyGraphicsEdge edge = nodeEdges.get(edgeIndex);
		MyGraphLine edgeLine = edge.getNode();
		
		Node node = edge.getStartGraphicsNode().getNode();
		Node destinationNode = this.nodes.get(destinationNodeIndex).getNode();
	
		edgeLine.setLinePosition(node, destinationNode);	
	}
	
	public void updateEdgesOfNodeAt(Integer nodeIndex) {
		/*it updates a edge line position when the node position was changed*/
		
		if(dataSource == null) {
			return;
		}
		
		Dictionary<Integer, MyGraphicsEdge> startingNodeEdges = this.nodes.get(nodeIndex).getStartingEdges();
		Enumeration<Integer> startingEdgesIndices = startingNodeEdges.keys();
		
		Dictionary<Integer, MyGraphicsEdge> endingNodeEdges = this.nodes.get(nodeIndex).getEndingEdges();
		Enumeration<Integer> endingEdgesIndices = endingNodeEdges.keys();
		
		Integer currentEdgeIndex;	
		while(startingEdgesIndices.hasMoreElements()) {
			currentEdgeIndex = startingEdgesIndices.nextElement();
			this.updateEdgeStartingInNodeAt(nodeIndex, currentEdgeIndex);
		}
		
		while(endingEdgesIndices.hasMoreElements()) {
			currentEdgeIndex = endingEdgesIndices.nextElement();
			this.updateEdgeEndingInNodeAt(nodeIndex, currentEdgeIndex);
		}
		
		
	}
	private void drawEdges() {
		/*
		 * iterate over all the nodes while drawing it edges
		 * */
		if(dataSource == null) {
			return ;
		}
		
		Enumeration<Integer> nodeIndices = this.nodes.keys();
		Integer currentNodeIndex;
		Integer currentNodeNumberOfEdges;
		Integer currentEdgeIndexFromNode;
		while(nodeIndices.hasMoreElements()) {
			
			currentNodeIndex = nodeIndices.nextElement();
			currentNodeNumberOfEdges = dataSource.graphDrawerNumberOfEdgesStartingFromNodeAtIndex(this, currentNodeIndex);
			
			if(currentNodeNumberOfEdges == null) {
				return;
			}
			
			for(currentEdgeIndexFromNode = 0; currentEdgeIndexFromNode < currentNodeNumberOfEdges; currentEdgeIndexFromNode++) {
				
				this.addEdgeToNodeAt(currentNodeIndex, currentEdgeIndexFromNode);
			}
		}
		
	}
	
	private void drawNodes() {
		/*
		 * it asks the datasource how each node must be drawed, and do it
		 * */
		
		if(dataSource == null) {
			return ;
		}
		
		Integer numberOfNodes = dataSource.graphDrawerNumberOfNodes(this);
		
		if(numberOfNodes == null) {
			return;
		}
		
		for(int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			addNodeAt(nodeIndex);
		}
	}
	
	private void setNodeInRandomUnusedPoint(MySize containerSize, Node targetNode) {
		/*
		 * set a node good position based on the container size, node size, and other nodes position, it will try a randomic position
		 * until find the good one
		 * */
		
		if(dataSource == null) {
			return;
		}
		
		Random random = new Random();
		
		MySize nodeMaxSize = dataSource.graphDrawerNodeMaxSize(this);
		
		if(nodeMaxSize == null) {
			nodeMaxSize = MyGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
		}

		int nodeMaxWidth = (int) Math.ceil(nodeMaxSize.getWidth().doubleValue());
		int nodeMaxHeight = (int) Math.ceil(nodeMaxSize.getHeight().doubleValue());
		
		int x = random.nextInt(containerSize.getWidth().intValue() - nodeMaxWidth);
		int y = random.nextInt(containerSize.getHeight().intValue() - nodeMaxHeight);
		
		targetNode.setLayoutX(x);
		targetNode.setLayoutY(y);		
	}

}
