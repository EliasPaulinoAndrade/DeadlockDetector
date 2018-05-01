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
		if(dataSource == null) {
			return ;
		}
		Color rootColor = dataSource.graphDrawerGraphColor(this);
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		
		Background rootBackground = new Background(new BackgroundFill(rootColor, null, null));
	
		this.setMinSize(rootSize.getWidth(), rootSize.getHeight());
		
		this.setBackground(rootBackground);
		
		drawNodes();
		drawEdges();
	}
	
	public void addNodeAt(Integer index) {
		/*draw a node at a index
		 * */
		Node node = dataSource.graphDrawerNodeViewForNodeAtIndex(this, index);
		MySize containerSize = dataSource.graphDrawerNodeMaxSize(this);
		MySize rootSize = new MySize(this.getWidth(), this.getHeight());

		Pane containerNode = new StackPane();
		
		MyGraphicsNode graphicsNode = new MyGraphicsNode(containerNode, index);

		containerNode.setMinHeight(containerSize.getHeight());
		containerNode.setMinWidth(containerSize.getWidth());

		containerNode.getChildren().add(node);
		setNodeInRandomUnusedPoint(rootSize, containerNode);
		
		this.nodes.put(index, graphicsNode);
		this.getChildren().add(containerNode);	
		
		if(dataSource.graphDrawerNodesCanMove(this)) {
			graphicsNode.setOnNodeDragged(this.onNodeDragListener);
			graphicsNode.setOnNodeMouseDown(this.onNodeMouseDown);
			graphicsNode.setOnNodeMouseUp(this.onNodeMouseUp);
		}
	}
	
	public void addEdgeToNodeAt(Integer nodeIndex, Integer edgeIndex){
		/*add a edge line from a node for it destination node*/
		
		MyGraphicsNode graphicsNode = this.nodes.get(nodeIndex);
		Node node = graphicsNode.getNode();
		Integer destinationNodeIndex = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, edgeIndex, nodeIndex);
		Node destinationNode = this.nodes.get(destinationNodeIndex).getNode();
		
		MyGraphLine currentLineEdge = new MyGraphLine(
				node.getLayoutX() + node.getBoundsInParent().getWidth()/2, 
				node.getLayoutY() + node.getBoundsInParent().getWidth()/2,
				destinationNode.getLayoutX() +  destinationNode.getBoundsInParent().getWidth()/2, 
				destinationNode.getLayoutY() + destinationNode.getBoundsInParent().getHeight()/2
				);
		currentLineEdge.setStroke(dataSource.graphDrawerEdgesColor(this));
		currentLineEdge.setStrokeWidth(dataSource.graphDrawerEdgeStrokeWidth(this));
		
		this.getChildren().add(currentLineEdge);
		graphicsNode.getEdges().put(edgeIndex, new MyGraphicsEdge(currentLineEdge));
		
		currentLineEdge.toBack();
	}
	
	public void updateEdgeOfNodeAt(Integer nodeIndex) {
		/*it updates a edge line position when the node position was changed*/
		
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
		
		for(int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			addNodeAt(nodeIndex);
		}
	}
	
	private void setNodeInRandomUnusedPoint(MySize containerSize, Node targetNode) {
		/*
		 * set a node good position based on the container size, node size, and other nodes position, it will try a randomic position
		 * until find the good one
		 * */
		
		Random random = new Random();

		int nodeMaxWidth = (int) Math.ceil(dataSource.graphDrawerNodeMaxSize(this).getWidth().doubleValue());
		int nodeMaxHeight = (int) Math.ceil(dataSource.graphDrawerNodeMaxSize(this).getHeight().doubleValue());
		
		int x = random.nextInt(containerSize.getWidth().intValue() - nodeMaxWidth);
		int y = random.nextInt(containerSize.getHeight().intValue() - nodeMaxHeight);
		
		targetNode.setLayoutX(x);
		targetNode.setLayoutY(y);		
	}

	private MyNodeEventHandler onNodeDragListener = new MyNodeEventHandler() {
		
		@Override
		public void handle(Event event) {
			MouseEvent mouseEvent = (MouseEvent) event;
			Node containerNode = (Node) event.getSource();
			MySize containerSize = dataSource.graphDrawerNodeMaxSize(MyGraphDrawer.this);
			
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

		@Override
		public void handle(Event event) {
			Pane containerNode = (Pane) event.getSource();
			BorderStroke stroke = new BorderStroke(
					Color.RED, 
					BorderStrokeStyle.SOLID, 
					new CornerRadii(containerNode.getWidth()/2), 
					new BorderWidths(2), 
					Insets.EMPTY);
			Border border = new Border(stroke);
			containerNode.setBorder(border);
		}
	};
	
	private MyNodeEventHandler onNodeMouseUp = new MyNodeEventHandler() {

		@Override
		public void handle(Event event) {
			Pane containerNode = (Pane) event.getSource();
			containerNode.setBorder(null);	
		}
	};
}
