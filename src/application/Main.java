package application;
	
import java.io.IOException;

import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MyGraphDrawerDelegate;
import application.graphDrawer.MySize;
import deadlock_detector.MyProcess;
import deadlock_detector.MyProcessNode;
import deadlock_detector.MyResource;
import deadlock_detector.MyResourceNode;
import graph.MyEdge;
import graph.MyGraph;
import graph.MyNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try {

			Pane root = setup();
			
			
			Scene scene = new Scene(root);
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
	
	private Pane setup() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("MainScreen.fxml"));
		Pane mainPane = loader.load();
	    
        return mainPane;
	}

}
