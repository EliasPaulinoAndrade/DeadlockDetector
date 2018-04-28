package application.graphDrawer;

import javafx.scene.Parent;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GraphDrawer extends Region{
	private GraphDrawerDataSource dataSource;
	
	public GraphDrawer() {
		super();
	}

	public GraphDrawerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(GraphDrawerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void drawGraphNodes() {
		if(dataSource == null) {
			return ;
		}
		MySize rootSize = dataSource.graphDrawerGraphSize(this);

		Background rootBackground = new Background(new BackgroundFill(Color.RED, null, null));

		this.setMinSize(rootSize.getWidth(), rootSize.getHeight());
		this.setBackground(rootBackground);
	}

}
