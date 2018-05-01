package application.datasources;

import application.ScreenConstants;
import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MySize;
import deadlock_detector.MyResource;
import deadlock_detector.MyResourceNode;
import graph.MyGraph;
import graph.MyNode;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
		VBox nodeView = new VBox();
		
		BorderStroke stroke = new BorderStroke(
				Color.GREY, 
				BorderStrokeStyle.SOLID, 
				new CornerRadii(10.0), 
				new BorderWidths(3), 
				Insets.EMPTY);
		
		Pane textCentralizer = new StackPane();
		
		Pane textContainer = new StackPane();
		textContainer.setBorder(new Border(stroke));
		textContainer.setMaxWidth(60);
		textCentralizer.getChildren().add(textContainer);
		
		Text nodeText = new Text("id: " + node.getValue().getStringValue());
		nodeText.setFill(Color.WHITE);
		textContainer.getChildren().add(nodeText);
		
		Pane nodeContainer = new StackPane();
		if(node instanceof MyResourceNode<?>) {
			MyResource resource = (MyResource) node.getValue();
			String resourceImagePath = ScreenConstants.defaultResourcesNames.get(resource.getName());
			if(resourceImagePath == null) {
				Rectangle nodeRect = new Rectangle(60, 60);
				nodeRect.setFill(Color.WHITE);
				nodeContainer.getChildren().add(nodeRect);
			}
			else {
				Image resourceImage = new Image(resourceImagePath);
				ImageView nodeImage = new ImageView(resourceImage);
				nodeContainer.getChildren().add(nodeImage);
			}
		}
		else {
			String processImagePath = ScreenConstants.defaultProcessImage;
			Image processImage = new Image(processImagePath);
			ImageView nodeImage = new ImageView(processImage);
			
			
			nodeContainer.getChildren().add(nodeImage);
		}

		nodeView.getChildren().addAll(nodeContainer, textCentralizer);
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

		return new MySize(100, 100);
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
		
		return Color.BLUE;
	}

	@Override
	public Double graphDrawerEdgeStrokeWidth(MyGraphDrawer graphDrawer) {

		return 3.0;
	}

	@Override
	public Boolean graphDrawerNodesCanMove(MyGraphDrawer graphDrawer) {
		
		return true;
	}

	@Override
	public Color graphDrawerTintColor(MyGraphDrawer graphDrawer) {
		
		return Color.CHARTREUSE;
	}
	

}
