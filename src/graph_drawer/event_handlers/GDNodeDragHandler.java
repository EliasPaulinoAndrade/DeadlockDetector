package graph_drawer.event_handlers;

import graph_drawer.GDGraphDrawer;
import graph_drawer.GDGraphDrawerDataSource;
import graph_drawer.GDGraphDrawerDefaultValues;
import graph_drawer.GDGraphicsNode;
import javafx.event.Event;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


/*when the node is dragged, it follows the mouse position(but only if the mouse is in the bounds)*/
public class GDNodeDragHandler implements GDNodeEventHandler{
	private GDGraphicsNode graphicsNode;
	private GDGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
		
		GDGraphDrawerDataSource dataSource = graphDrawer.getDataSource();
		if(dataSource == null) {
			return;
		}
		
		Dimension2D containerSize = dataSource.graphDrawerNodeMaxSize(graphDrawer);
		
		if(containerSize == null) {
			containerSize = GDGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
		}
		
		MouseEvent mouseEvent = (MouseEvent) event;
		Node containerNode = (Node) event.getSource();
		
		Double hTranslation = containerNode.getLayoutX() + mouseEvent.getX() - containerSize.getWidth()/2;
		Double vTranslation = containerNode.getLayoutY() + mouseEvent.getY() - containerSize.getHeight()/2;
		
		Boolean changed = false;
		if(hTranslation > 0 && hTranslation + containerSize.getWidth() < graphDrawer.getWidth()) {
			containerNode.setLayoutX(hTranslation);
			changed = true;
		}
		if(vTranslation > 0 && vTranslation + containerSize.getHeight() < graphDrawer.getHeight()) {
			containerNode.setLayoutY(vTranslation);
			changed = true;
		}
		if(changed) {
			graphDrawer.updateEdgesOfNodeAt(getGraphicsNode().getIndex());
		}
	}
	
	public GDNodeDragHandler(GDGraphicsNode graphicsNode, GDGraphDrawer graphDrawer) {
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
