package application.graphDrawer.eventHandlers;

import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphicsNode;
import javafx.event.Event;
import javafx.scene.layout.Pane;

/*when the mouse leaves the node, the node border is removed
 * */
public class MyNodeClickUpHandler implements MyNodeEventHandler{
	
	private MyGraphicsNode graphicsNode;
	private MyGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
		Pane containerNode = (Pane) event.getSource();
		containerNode.setBorder(null);	
	}
	
	public MyNodeClickUpHandler(MyGraphicsNode graphicsNode, MyGraphDrawer graphDrawer) {
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
