package application.datasources;

import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MySize;
import deadlock_detector.MyResourceNode;
import graph.MyGraph;
import graph.MyNode;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/*data source implementation for deadlock threads problem*/
public class MyDeadlockGraphDataSource implements MyGraphDrawerDataSource{
	private MyGraph graph;
	private Pane graphContainer;
	
	public MyDeadlockGraphDataSource(MyGraph graph, Pane graphContainer) {
		super();
		this.graph = graph;
		this.graphContainer = graphContainer;
	}

	@Override
	public Integer graphDrawerNumberOfNodes(MyGraphDrawer graphDrawer) {
		
		return this.graph.numberOfNodes();
	}

	@Override
	public Node graphDrawerNodeViewForNodeAtIndex(MyGraphDrawer graphDrawer, Integer index) {
		
		MyNode<?> node = this.graph.getNodeAt(index);
		StackPane nodeView = new StackPane();
		Text nodeText = new Text(node.getValue().getStringValue());
		if(node instanceof MyResourceNode<?>) {
			Rectangle nodeRect = new Rectangle(40, 40);
			nodeRect.setFill(Color.WHITE);
			
			nodeView.getChildren().addAll(nodeRect, nodeText);
		}
		else {
			Circle nodeCircle = new Circle(20);
			nodeCircle.setFill(Color.WHITE);
			
			nodeView.getChildren().addAll(nodeCircle, nodeText);
		}
		
		return nodeView;
	}

	@Override
	public MySize graphDrawerGraphSize(MyGraphDrawer graphDrawer) {
	
		return new MySize(graphContainer.getPrefWidth(), graphContainer.getPrefHeight());
	}

	@Override
	public Color graphDrawerGraphColor(MyGraphDrawer graphDrawer) {
		
		return Color.BLACK;
	}

	@Override
	public MySize graphDrawerNodeMaxSize(MyGraphDrawer graphDrawer) {

		return new MySize(50, 50);
	}

	@Override
	public Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer index) {

		return this.graph.getNodeAt(index).numberOfEdges();
	}

	@Override
	public Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex) {
		return this.graph.getNodeAt(nodeIndex).getEdgeAt(edgeIndex).getDestinationVertex().getId();
	}

	@Override
	public Color graphDrawerEdgesColor(MyGraphDrawer graphDrawer) {
		
		return Color.RED;
	}

	@Override
	public Double graphDrawerEdgeStrokeWidth(MyGraphDrawer graphDrawer) {

		return 3.0;
	}

	@Override
	public Boolean graphDrawerNodesCanMove(MyGraphDrawer graphDrawer) {
		
		return true;
	}
	

}
