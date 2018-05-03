package graphDrawer.eventHandlers;

import graphDrawer.GDGraphDrawer;
import graphDrawer.GDGraphDrawerDataSource;
import graphDrawer.GDGraphDrawerDefaultValues;
import graphDrawer.GDGraphicsNode;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/*when the mouse enters on the node, a node border is set*/
public class GDNodeClickDownHandler implements GDNodeEventHandler{
	
	private GDGraphicsNode graphicsNode;
	private GDGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
	
		GDGraphDrawerDataSource dataSource = graphDrawer.getDataSource();
		if(dataSource == null) {
			return;
		}
		
		Color tintColor = dataSource.graphDrawerTintColor(graphDrawer);
		
		if(tintColor == null) {
			tintColor = GDGraphDrawerDefaultValues.graphDrawerTintColor;
		}
		
		Pane containerNode = (Pane) event.getSource();
		BorderStroke stroke = new BorderStroke(
				dataSource.graphDrawerTintColor(graphDrawer), 
				BorderStrokeStyle.SOLID, 
				new CornerRadii(containerNode.getWidth()/2), 
				new BorderWidths(2), 
				Insets.EMPTY);
		Border border = new Border(stroke);
		containerNode.setBorder(border);
	}

	public GDNodeClickDownHandler(GDGraphicsNode graphicsNode, GDGraphDrawer graphDrawer) {
		super();
		this.graphicsNode = graphicsNode;
		this.graphDrawer = graphDrawer;
	}

	@Override
	public GDGraphicsNode getGraphicsNode() {
		return this.graphicsNode;
	}

	@Override
	public void setGraphicsNode(GDGraphicsNode graphicsNode) {
		this.graphicsNode = graphicsNode;
		
	}

	@Override
	public GDGraphDrawer getMyGraphDrawer() {
		return this.graphDrawer;
	}

	@Override
	public void setMyGraphDrawer(GDGraphDrawer graphDrawer) {
		this.graphDrawer = graphDrawer;
	}
	
}
