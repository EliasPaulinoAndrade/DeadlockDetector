package application.graphDrawer;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface GraphDrawerDataSource {
	Integer graphDrawerNumberOfNodes(GraphDrawer graphDrawer);
	Node graphDrawerNodeViewForIndex(GraphDrawer graphDrawer, Integer index);
	MySize graphDrawerGraphSize(GraphDrawer graphDrawer);
	Color graphDrawerGraphColor(GraphDrawer graphDrawer);
}
