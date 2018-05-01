package application.graphDrawer.eventHandlers;

import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MyGraphDrawerDefaultValues;
import application.graphDrawer.MyGraphicsNode;
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
public class MyNodeClickDownHandler implements MyNodeEventHandler{
	
	private MyGraphicsNode graphicsNode;
	private MyGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
	
		MyGraphDrawerDataSource dataSource = graphDrawer.getDataSource();
		if(dataSource == null) {
			return;
		}
		
		Color tintColor = dataSource.graphDrawerTintColor(graphDrawer);
		
		if(tintColor == null) {
			tintColor = MyGraphDrawerDefaultValues.graphDrawerTintColor;
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

	public MyNodeClickDownHandler(MyGraphicsNode graphicsNode, MyGraphDrawer graphDrawer) {
		super();
		this.graphicsNode = graphicsNode;
		this.graphDrawer = graphDrawer;
	}

	@Override
	public MyGraphicsNode getGraphicsNode() {
		return this.graphicsNode;
	}

	@Override
	public void setGraphicsNode(MyGraphicsNode graphicsNode) {
		this.graphicsNode = graphicsNode;
		
	}

	@Override
	public MyGraphDrawer getMyGraphDrawer() {
		return this.graphDrawer;
	}

	@Override
	public void setMyGraphDrawer(MyGraphDrawer graphDrawer) {
		this.graphDrawer = graphDrawer;
	}
	
}
