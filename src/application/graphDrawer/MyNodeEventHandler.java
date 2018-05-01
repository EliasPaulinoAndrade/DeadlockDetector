package application.graphDrawer;


import javafx.event.Event;
import javafx.event.EventHandler;

/*its a EventHandle extensions which can have a graphicsNode reference*/

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
