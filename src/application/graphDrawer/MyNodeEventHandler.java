package application.graphDrawer;


import javafx.event.Event;
import javafx.event.EventHandler;
public class MyNodeEventHandler implements EventHandler<Event>{
	private MyGraphicsNode graphicsNode;
	
	@Override
	public void handle(Event arg0) {
		
	}
	
	public MyGraphicsNode getGraphicsNode() {
		return graphicsNode;
	}

	public void setGraphicsNode(MyGraphicsNode graphicsNode) {
		this.graphicsNode = graphicsNode;
	}
}
