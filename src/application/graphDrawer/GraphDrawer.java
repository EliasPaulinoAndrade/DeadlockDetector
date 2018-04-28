package application.graphDrawer;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

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
		Color rootColor = dataSource.graphDrawerGraphColor(this);
		MySize rootSize = dataSource.graphDrawerGraphSize(this);
		Integer numberOfNodes = dataSource.graphDrawerNumberOfNodes(this);


		Background rootBackground = new Background(new BackgroundFill(rootColor, null, null));

		this.setMinSize(rootSize.getWidth(), rootSize.getHeight());
		this.setBackground(rootBackground);
	}

}
