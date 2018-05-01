package application.graphDrawer.eventHandlers;


import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphicsNode;
import javafx.event.Event;
import javafx.event.EventHandler;

/*its a EventHandle extensions which can have a graphicsNode reference*/

public interface MyNodeEventHandler extends EventHandler<Event>{
	
	public MyGraphDrawer getMyGraphDrawer();
	
	public void setMyGraphDrawer(MyGraphDrawer graphDrawer);
	
	public MyGraphicsNode getGraphicsNode();

	public void setGraphicsNode(MyGraphicsNode graphicsNode);
}
