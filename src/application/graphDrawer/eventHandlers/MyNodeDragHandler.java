package application.graphDrawer.eventHandlers;

import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MyGraphDrawerDefaultValues;
import application.graphDrawer.MyGraphicsNode;
import application.graphDrawer.MySize;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


/*when the node is dragged, it follows the mouse position(but only if the mouse is in the bounds)*/
public class MyNodeDragHandler implements MyNodeEventHandler{
	private MyGraphicsNode graphicsNode;
	private MyGraphDrawer graphDrawer;
	
	@Override
	public void handle(Event event) {
		
		MyGraphDrawerDataSource dataSource = graphDrawer.getDataSource();
		if(dataSource == null) {
			return;
		}
		
		MySize containerSize = dataSource.graphDrawerNodeMaxSize(graphDrawer);
		
		if(containerSize == null) {
			containerSize = MyGraphDrawerDefaultValues.graphDrawerNodeMaxSize;
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
	
	public MyNodeDragHandler(MyGraphicsNode graphicsNode, MyGraphDrawer graphDrawer) {
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
