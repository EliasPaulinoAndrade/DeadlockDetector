package application.graphDrawer;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface GraphDrawerDataSource {
	Integer graphDrawerNumberOfNodes(GraphDrawer graphDrawer);
	Node graphDrawerNodeViewForNodeAtIndex(GraphDrawer graphDrawer, Integer index);
	MySize graphDrawerGraphSize(GraphDrawer graphDrawer);
	Color graphDrawerGraphColor(GraphDrawer graphDrawer);
	Color graphDrawerEdgesColor(GraphDrawer graphDrawer);
	MySize graphDrawerNodeMaxSize(GraphDrawer graphDrawer);
	Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(GraphDrawer graphDrawer, Integer index);
	Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(GraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex);
	Double graphDrawerMinDistanceBetweenNodes(GraphDrawer graphDrawer);
}
