package application.graphDrawer;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
		
		if(node == null) {
			return ;
		}
		if(containerSize == null) {
			containerSize = MyGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
		}
		
		MySize rootSize = new MySize(this.getWidth(), this.getHeight());
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
			graphicsNode.setOnNodeDragged(this.onNodeDragListener);
			graphicsNode.setOnNodeMouseDown(this.onNodeMouseDown);
			graphicsNode.setOnNodeMouseUp(this.onNodeMouseUp);
		}
	}
	
	public void addEdgeToNodeAt(Integer nodeIndex, Integer edgeIndex){
		/*add a edge line from a node for it destination node*/
		
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
		
		
		MyGraphicsNode graphicsNode = this.nodes.get(nodeIndex);
		Node node = graphicsNode.getNode();
		Node destinationNode = this.nodes.get(destinationNodeIndex).getNode();
		
		MyGraphLine currentLineEdge = new MyGraphLine(
				node.getLayoutX() + node.getBoundsInParent().getWidth()/2, 
				node.getLayoutY() + node.getBoundsInParent().getWidth()/2,
				destinationNode.getLayoutX() +  destinationNode.getBoundsInParent().getWidth()/2, 
				destinationNode.getLayoutY() + destinationNode.getBoundsInParent().getHeight()/2
				);
		currentLineEdge.setStroke(edgeColor);
		currentLineEdge.setStrokeWidth(edgeWidth);
		
		this.getChildren().add(currentLineEdge);
		graphicsNode.getEdges().put(edgeIndex, new MyGraphicsEdge(currentLineEdge));
		
		currentLineEdge.toBack();
	}
	
	public void updateEdgeOfNodeAt(Integer nodeIndex) {
		/*it updates a edge line position when the node position was changed*/
		
		if(dataSource == null) {
			return;
		}
		
		Dictionary<Integer, MyGraphicsEdge> nodeEdges = this.nodes.get(nodeIndex).getEdges();
		
		Enumeration<Integer> edgesIndices = nodeEdges.keys();
		Integer currentEdgeIndex;
		Integer destinationNodeIndexForCurrentEdge;
		
		Node node = this.nodes.get(nodeIndex).getNode();
		Node destinationNode;
		MyGraphLine currentEdgeLine;
		
		while(edgesIndices.hasMoreElements()) {
			currentEdgeIndex = edgesIndices.nextElement();
			destinationNodeIndexForCurrentEdge = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, currentEdgeIndex, nodeIndex);
			
			if(destinationNodeIndexForCurrentEdge == null) {
				return;
			}
			
			destinationNode = this.nodes.get(destinationNodeIndexForCurrentEdge).getNode();
			currentEdgeLine = nodeEdges.get(currentEdgeIndex).getNode();
			
			currentEdgeLine.setLinePosition(
					node.getLayoutX() + node.getBoundsInParent().getWidth()/2, 
					node.getLayoutY() + node.getBoundsInParent().getWidth()/2,
					destinationNode.getLayoutX() +  destinationNode.getBoundsInParent().getWidth()/2, 
					destinationNode.getLayoutY() + destinationNode.getBoundsInParent().getHeight()/2
					);
			
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

	private MyNodeEventHandler onNodeDragListener = new MyNodeEventHandler() {
		/*when the node is dragged, it follows the mouse position(but only if the mouse is in the bounds)*/
		
		@Override
		public void handle(Event event) {
			if(dataSource == null) {
				return;
			}
			
			MySize containerSize = dataSource.graphDrawerNodeMaxSize(MyGraphDrawer.this);
			
			if(containerSize == null) {
				containerSize = MyGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
			}
			
			MouseEvent mouseEvent = (MouseEvent) event;
			Node containerNode = (Node) event.getSource();
			
			Double hTranslation = containerNode.getLayoutX() + mouseEvent.getX() - containerSize.getWidth()/2;
			Double vTranslation = containerNode.getLayoutY() + mouseEvent.getY() - containerSize.getHeight()/2;
			
			Boolean changed = false;
			if(hTranslation > 0 && hTranslation + containerSize.getWidth() < MyGraphDrawer.this.getWidth()) {
				containerNode.setLayoutX(hTranslation);
				changed = true;
			}
			if(vTranslation > 0 && vTranslation + containerSize.getHeight() < MyGraphDrawer.this.getHeight()) {
				containerNode.setLayoutY(vTranslation);
				changed = true;
			}
			if(changed) {
				MyGraphDrawer.this.updateEdgeOfNodeAt(getGraphicsNode().getIndex());
			}
		}
	
	};

	private MyNodeEventHandler onNodeMouseDown = new MyNodeEventHandler() {
		/*when the mouse enters on the node, a node border is set*/
		
		@Override
		public void handle(Event event) {
		
			if(dataSource == null) {
				return;
			}
			
			Color tintColor = dataSource.graphDrawerTintColor(MyGraphDrawer.this);
			
			if(tintColor == null) {
				tintColor = MyGraphDrawerDefaultValues.graphDrawerTintColor;
			}
			
			Pane containerNode = (Pane) event.getSource();
			BorderStroke stroke = new BorderStroke(
					dataSource.graphDrawerTintColor(MyGraphDrawer.this), 
					BorderStrokeStyle.SOLID, 
					new CornerRadii(containerNode.getWidth()/2), 
					new BorderWidths(2), 
					Insets.EMPTY);
			Border border = new Border(stroke);
			containerNode.setBorder(border);
		}
	};
	
	private MyNodeEventHandler onNodeMouseUp = new MyNodeEventHandler() {
		/*when the mouse leaves the node, the node border is removed
		 * */
		@Override
		public void handle(Event event) {
			Pane containerNode = (Pane) event.getSource();
			containerNode.setBorder(null);	
		}
	};
}
