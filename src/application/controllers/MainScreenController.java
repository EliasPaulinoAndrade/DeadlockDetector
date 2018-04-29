package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import deadlock_detector.MyResource;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainScreenController implements Initializable{
	@FXML private Button addButton;
	@FXML private TextField resourceName;
	@FXML private TextField resourceId;
	@FXML private TableView<MyResource> tableView;
	
	private List<MyResource> resources;
	
	public MainScreenController() {
		resources = new ArrayList<>();
	}

	@FXML 
	private void handleButtonAction(ActionEvent event) {
		resources.add(new MyResource(resourceName.getText(), resourceId.getText()));
		System.out.println(this.resourceName.getText());
		
		ObservableList<TableColumn<MyResource, ?>> columns = tableView.getColumns();

		for(TableColumn<?, ?> column : columns) {
			System.out.println(column.getText());
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
