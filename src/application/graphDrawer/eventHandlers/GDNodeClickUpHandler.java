package application.graphDrawer.eventHandlers;

import application.graphDrawer.GDGraphDrawer;
import application.graphDrawer.GDGraphicsNode;
import javafx.event.Event;
import javafx.scene.layout.Pane;

/*when the mouse leaves the node, the node border is removed
 * */
public class GDNodeClickUpHandler implements GDNodeEventHandler{
	
	private GDGraphicsNode graphicsNode;
	private GDGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
		Pane containerNode = (Pane) event.getSource();
		containerNode.setBorder(null);	
	}
	
	public GDNodeClickUpHandler(GDGraphicsNode graphicsNode, GDGraphDrawer graphDrawer) {
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
