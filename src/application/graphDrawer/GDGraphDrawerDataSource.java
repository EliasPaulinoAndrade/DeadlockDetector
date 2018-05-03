package application.graphDrawer;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;



/*implementations of this interfaces will be resposible for say especifications about the graph view*/
public interface GDGraphDrawerDataSource {
	Integer graphDrawerNumberOfNodes(GDGraphDrawer graphDrawer);
	Node graphDrawerNodeViewForNodeAtIndex(GDGraphDrawer graphDrawer, Integer index);
	Dimension2D graphDrawerGraphSize(GDGraphDrawer graphDrawer);
	Color graphDrawerGraphColor(GDGraphDrawer graphDrawer);
	Color graphDrawerEdgesColor(GDGraphDrawer graphDrawer);
	Dimension2D graphDrawerNodeMaxSize(GDGraphDrawer graphDrawer);
	Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(GDGraphDrawer graphDrawer, Integer index);
	Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(GDGraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex);
	Double graphDrawerEdgeStrokeWidth(GDGraphDrawer graphDrawer);
	Boolean graphDrawerNodesCanMove(GDGraphDrawer graphDrawer);
	Color graphDrawerTintColor(GDGraphDrawer graphDrawer);
	GDEdgeStyle graphDrawerStyleForEdgeOfNodeAt(GDGraphDrawer graphDrawer, Integer nodeIndex, Integer edgeIndex);
}
