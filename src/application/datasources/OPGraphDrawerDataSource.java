package application.datasources;

import application.ScreenConstants;
import graphDrawer.GDEdgeStyle;
import graphDrawer.GDGraphDrawer;
import graphDrawer.GDGraphDrawerDataSource;
import application.deadlock_detector.OPResource;
import application.deadlock_detector.OPResourceNode;
import graph.GPGraph;
import graph.GPNode;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/*data source implementation for deadlock threads problem*/

public class OPGraphDrawerDataSource implements GDGraphDrawerDataSource{
	private GPGraph graph;
	private Pane graphContainer;
	
	public OPGraphDrawerDataSource(GPGraph graph, Pane graphContainer) {
		super();
		this.graph = graph;
		this.graphContainer = graphContainer;
	}

	@Override
	public Integer graphDrawerNumberOfNodes(GDGraphDrawer graphDrawer) {
		
		return this.graph.numberOfNodes();
	}

	@Override
	public Node graphDrawerNodeViewForNodeAtIndex(GDGraphDrawer graphDrawer, Integer index) {
		
		GPNode<?> node = this.graph.getNodeAt(index);
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
		if(node instanceof OPResourceNode<?>) {
			OPResource resource = (OPResource) node.getValue();
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
	public Dimension2D graphDrawerGraphSize(GDGraphDrawer graphDrawer) {
	
		return new Dimension2D(graphContainer.getPrefWidth(), graphContainer.getPrefHeight());
	}

	@Override
	public Color graphDrawerGraphColor(GDGraphDrawer graphDrawer) {
		
		return Color.BLACK;
	}

	@Override
	public Dimension2D graphDrawerNodeMaxSize(GDGraphDrawer graphDrawer) {

		return new Dimension2D(100, 100);
	}

	@Override
	public Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(GDGraphDrawer graphDrawer, Integer index) {

		return this.graph.getNodeAt(index).numberOfEdges();
	}

	@Override
	public Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(GDGraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex) {
		return this.graph.getNodeAt(nodeIndex).getEdgeAt(edgeIndex).getDestinationVertex().getId();
	}

	@Override
	public Color graphDrawerEdgesColor(GDGraphDrawer graphDrawer) {
		
		return Color.BLUE;
	}

	@Override
	public Double graphDrawerEdgeStrokeWidth(GDGraphDrawer graphDrawer) {

		return 3.0;
	}

	@Override
	public Boolean graphDrawerNodesCanMove(GDGraphDrawer graphDrawer) {
		
		return true;
	}

	@Override
	public Color graphDrawerTintColor(GDGraphDrawer graphDrawer) {
		
		return Color.CHARTREUSE;
	}

	@Override
	public GDEdgeStyle graphDrawerStyleForEdgeOfNodeAt(GDGraphDrawer graphDrawer, Integer nodeIndex,
			Integer edgeIndex) {
		GPNode<?> destinationVertex =  this.graph.getNodeAt(nodeIndex).getEdgeAt(edgeIndex).getDestinationVertex();
		
		if(destinationVertex instanceof OPResourceNode<?>) {
			return GDEdgeStyle.MEDIUM_DOTTED;
		}
		return GDEdgeStyle.SOLID;
	}
	

}
