package application.graphDrawer;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/*Some times, the values returned by the datasource methods may be null, this class povides
 * some default values to use on the graph.
 * */

public class MyGraphDrawerDefaultValues {
	public static final MySize graphDrawerGraphSize = new MySize(500, 500);
	public static final Color graphDrawerGraphColor = Color.BLACK;
	public static final Color graphDrawerEdgesColor = Color.RED;
	public static final MySize graphDrawerNodeMaxSize = new MySize(500, 500);
	public static final Double graphDrawerEdgeStrokeWidth = 3.0;
	public static final Boolean graphDrawerNodesCanMove = true;
	public static final Color graphDrawerTintColor = Color.RED;

}
