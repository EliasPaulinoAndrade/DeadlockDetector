package application.graphDrawer;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

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
	
	private Dictionary<Integer, Node> nodes;
	
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
		Node currentNode;
		Integer currentDestination;
		Node destinationNode;
		MyGraphLine currentLineEdge;
		while(nodeIndices.hasMoreElements()) {
			
			currentNodeIndex = nodeIndices.nextElement();
			currentNode = this.nodes.get(currentNodeIndex);
			currentNodeNumberOfEdges = dataSource.graphDrawerNumberOfEdgesStartingFromNodeAtIndex(this, currentNodeIndex);
			
			for(currentEdgeIndexFromNode = 0; currentEdgeIndexFromNode < currentNodeNumberOfEdges; currentEdgeIndexFromNode++) {
				currentDestination = dataSource.graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(this, currentEdgeIndexFromNode, currentNodeIndex);
				destinationNode = this.nodes.get(currentDestination);
				
				currentLineEdge = new MyGraphLine(
						currentNode.getLayoutX() + currentNode.getBoundsInParent().getWidth()/2, 
						currentNode.getLayoutY() + currentNode.getBoundsInParent().getWidth()/2,
						destinationNode.getLayoutX() +  destinationNode.getBoundsInParent().getWidth()/2, 
						destinationNode.getLayoutY() + destinationNode.getBoundsInParent().getHeight()/2
						);
				
				
				currentLineEdge.setStroke(dataSource.graphDrawerEdgesColor(this));
				currentLineEdge.setStrokeWidth(dataSource.graphDrawerEdgeStrokeWidth(this));
				this.getChildren().add(currentLineEdge);
			
				currentLineEdge.toBack();
				
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
		
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		Integer numberOfNodes = dataSource.graphDrawerNumberOfNodes(this);
		MySize containerSize = dataSource.graphDrawerNodeMaxSize(this);
		
		Node currentNode;
		Pane containerNode;
		for(int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			containerNode = new StackPane();
			containerNode.minWidth(containerSize.getWidth());
			containerNode.minHeight(containerSize.getHeight());
			
			currentNode = dataSource.graphDrawerNodeViewForNodeAtIndex(this, nodeIndex);
			
			containerNode.getChildren().add(currentNode);
			setNodeInRandomUnusedPoint(rootSize, currentNode);
			this.nodes.put(nodeIndex, currentNode);
			this.getChildren().add(currentNode);
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
		
		Boolean intersects = true;
		
		while(intersects == true) {
			int x = random.nextInt(containerSize.getWidth().intValue() - nodeMaxWidth);
			int y = random.nextInt(containerSize.getHeight().intValue() - nodeMaxHeight);
			
			targetNode.setLayoutX(x);
			targetNode.setLayoutY(y);
			
			intersects = checkSafePoint(targetNode);
		}
		
	}
	
	private Boolean checkSafePoint(Node targetNode) {
		/*
		 * check if the target node is in a good position: it never intersects other nodes of the graph
		 * 
		 * */
		
		Enumeration<Node> nodes = this.nodes.elements();
		Node currentNode;
		
		while(nodes.hasMoreElements()) {
			currentNode = nodes.nextElement();
			if (currentNode.getBoundsInParent().intersects(targetNode.getBoundsInParent())){
				System.out.print("fuck");
				return true;
			}
		}
		return false;
	}

}
