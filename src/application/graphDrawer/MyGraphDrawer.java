package application.graphDrawer;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class MyGraphDrawer extends Region{
	private MyGraphDrawerDataSource dataSource;
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
				this.getChildren().add(currentLineEdge);
			
				currentLineEdge.toBack();
				
			}
		}
	}
	
	private void drawNodes() {
		if(dataSource == null) {
			return ;
		}
		
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		Integer numberOfNodes = dataSource.graphDrawerNumberOfNodes(this);
		
		Node currentNode;
		for(int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			currentNode = dataSource.graphDrawerNodeViewForNodeAtIndex(this, nodeIndex);
			setNodeInRandomUnusedPoint(rootSize, currentNode);
			this.nodes.put(nodeIndex, currentNode);
			this.getChildren().add(currentNode);
		}
	}
	
	private void setNodeInRandomUnusedPoint(MySize containerSize, Node targetNode) {
		Random random = new Random();

		int nodeMaxWidth = (int) Math.ceil(dataSource.graphDrawerNodeMaxSize(this).getWidth().doubleValue());
		int nodeMaxHeight = (int) Math.ceil(dataSource.graphDrawerNodeMaxSize(this).getHeight().doubleValue());
		
		Boolean intersects = true;
		while(intersects == true) {
			int x = random.nextInt(containerSize.getWidth().intValue() - nodeMaxWidth);
			int y = random.nextInt(containerSize.getHeight().intValue() - nodeMaxHeight);
			
			targetNode.setLayoutX(x);
			targetNode.setLayoutY(y);
			
			intersects = checkUsedPoint(targetNode);
		}
		
	}
	
	private Boolean checkUsedPoint(Node targetNode) {
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
