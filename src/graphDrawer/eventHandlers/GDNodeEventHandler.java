package graphDrawer.eventHandlers;


import graphDrawer.GDGraphDrawer;
import graphDrawer.GDGraphicsNode;
import javafx.event.Event;
import javafx.event.EventHandler;

/*its a EventHandle extensions which can have a graphicsNode reference*/

public interface GDNodeEventHandler extends EventHandler<Event>{
	
	public GDGraphDrawer getMyGraphDrawer();
	
	public void setMyGraphDrawer(GDGraphDrawer graphDrawer);
	
	public GDGraphicsNode getGraphicsNode();

	public void setGraphicsNode(GDGraphicsNode graphicsNode);
}
