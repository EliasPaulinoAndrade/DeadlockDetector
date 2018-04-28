package application.graphDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.javafx.geom.BoxBounds;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GraphDrawer extends Region{
	private GraphDrawerDataSource dataSource;
	private List<Node> nodes;
	
	public GraphDrawer() {
		super();
		this.nodes = new ArrayList<>();
		
	}

	public GraphDrawerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(GraphDrawerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void drawGraphNodes() {
		if(dataSource == null) {
			return ;
		}
		Color rootColor = dataSource.graphDrawerGraphColor(this);
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		Integer numberOfNodes = dataSource.graphDrawerNumberOfNodes(this);
		Background rootBackground = new Background(new BackgroundFill(rootColor, null, null));

		this.setMinSize(rootSize.getWidth(), rootSize.getHeight());
		this.setBackground(rootBackground);
	
		
		Node currentNode;
		for(int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			currentNode = dataSource.graphDrawerNodeViewForIndex(this, nodeIndex);
			setNodeInRandomUnusedPoint(rootSize, currentNode);
			this.nodes.add(currentNode);
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
		for(Node node : this.nodes) {
			if (node.getBoundsInParent().intersects(targetNode.getBoundsInParent())){
				System.out.print("fuck");
				return true;
			}
		}
		return false;
	}
}
