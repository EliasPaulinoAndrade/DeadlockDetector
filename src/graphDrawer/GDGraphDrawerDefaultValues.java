package graphDrawer;

import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

/*Some times, the values returned by the datasource methods may be null, this class povides
 * some default values to use on the graph.
 * */

public class GDGraphDrawerDefaultValues {
	public static final Dimension2D graphDrawerGraphSize = new Dimension2D(500, 500);
	public static final Color graphDrawerGraphColor = Color.BLACK;
	public static final Color graphDrawerEdgesColor = Color.RED;
	public static final Dimension2D graphDrawerNodeMaxSize = new Dimension2D(500, 500);
	public static final Double graphDrawerEdgeStrokeWidth = 3.0;
	public static final Boolean graphDrawerNodesCanMove = true;
	public static final Color graphDrawerTintColor = Color.RED;
	public static final GDEdgeStyle graphDrawerStyleForEdgeOfNodeAt = GDEdgeStyle.SOLID;

}
