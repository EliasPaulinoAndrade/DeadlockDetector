package application.log;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Log{
	
	public TableView<Record> tab1;
	public Stage s;
	
	@SuppressWarnings("unchecked")
	public Log(){
		s = new Stage();
		s.setTitle("System Log");
		
		TableColumn<Record, String> col1 = new TableColumn<>("Processo");
		TableColumn<Record, String> col2 = new TableColumn<>("Ação");
		TableColumn<Record, String> col3 = new TableColumn<>("Recurso");
		col1.setMinWidth(50);
		col2.setMinWidth(250);
		col3.setMinWidth(50);
		col1.setStyle("-fx-alignment: CENTER;");
		//col2.setStyle("-fx-alignment: CENTER;");
		col1.setCellValueFactory(new PropertyValueFactory<Record,String>("idProcess"));
		col2.setCellValueFactory(new PropertyValueFactory<Record,String>("record"));
		col3.setCellValueFactory(new PropertyValueFactory<Record,String>("resource"));
		
		tab1 = new TableView<>();
		tab1.getColumns().addAll(col1, col2, col3);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(tab1);
		
		//s.setResizable(false);
		s.setScene(new Scene(vb));
		s.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
		s.initStyle(StageStyle.UNIFIED);
		s.show();
		s.toBack();
	}
	
	public void writeInLog(String idProcess, String record, String resource) {
		Record rec = new Record(idProcess, record, resource);
		tab1.getItems().add(0, rec);
	}

}
