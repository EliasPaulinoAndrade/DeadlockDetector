package application;
	
import application.graphDrawer.GraphDrawer;
import application.graphDrawer.GraphDrawerDataSource;
import application.graphDrawer.MySize;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Main extends Application implements GraphDrawerDataSource {
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(setup(),600,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Pane setup() {
		GraphDrawer btn = new GraphDrawer();
		btn.setDataSource(this);
		
		btn.drawGraphNodes();
		
        Pane root = new Pane();
		
        root.getChildren().add(btn);
        
        return root;
	}

	@Override
	public Integer graphDrawerNumberOfNodes(GraphDrawer graphDrawer) {
		
		return null;
	}

	@Override
	public Node graphDrawerNodeViewForIndex(GraphDrawer graphDrawer, Integer index) {
		
		return null;
	}

	@Override
	public MySize graphDrawerGraphSize(GraphDrawer graphDrawer) {
	
		return new MySize(400, 400);
	}

	@Override
	public Color graphDrawerGraphColor(GraphDrawer graphDrawer) {
		
		return null;
	}
}
