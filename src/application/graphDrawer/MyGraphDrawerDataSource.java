package application.graphDrawer;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface MyGraphDrawerDataSource {
	Integer graphDrawerNumberOfNodes(MyGraphDrawer graphDrawer);
	Node graphDrawerNodeViewForNodeAtIndex(MyGraphDrawer graphDrawer, Integer index);
	MySize graphDrawerGraphSize(MyGraphDrawer graphDrawer);
	Color graphDrawerGraphColor(MyGraphDrawer graphDrawer);
	Color graphDrawerEdgesColor(MyGraphDrawer graphDrawer);
	MySize graphDrawerNodeMaxSize(MyGraphDrawer graphDrawer);
	Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer index);
	Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex);
	Double graphDrawerEdgeStrokeWidth(MyGraphDrawer graphDrawer);
}
