package application;
	
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

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
	    loader.setLocation(Main.class.getResource("screens/MainScreen.fxml"));
		Pane mainPane = loader.load();
	    
        return mainPane;
	}

}
