package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import deadlock_detector.MyResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class MainScreenController implements Initializable{
	@FXML private Button addButton;
	@FXML private TextField resourceName;
	@FXML private TextField resourceId;
	@FXML private VBox tableViewContainer;
	@FXML private CheckBox autoIncrementCheck;
	
	private TableView<MyResource> resourcesTableView;
	private TableColumn<MyResource, String> column;
	private TableColumn<MyResource, String> column2;
	
	private List<MyResource> myResources;
	
	public MainScreenController() {
		myResources = new ArrayList<>();
	}

	@FXML 
	private void handleButtonAction(ActionEvent event) {
		MyResource resource  = new MyResource(
				resourceName.getText(), 
				resourceId.getText()
				);
	
		myResources.add(resource);
		resourcesTableView.getItems().add(resource);		
		if(this.autoIncrementCheck.isSelected()) {
			Integer nextResourceId = Integer.parseInt(this.resourceId.getText()) + 1;
			this.resourceId.setText(nextResourceId.toString());
		}
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {
		if(autoIncrementCheck.isSelected()) {
			this.resourceId.setEditable(false);
			this.resourceId.setDisable(true);
			Integer resourcesSize = this.myResources.size();
			if(resourcesSize > 0) {
				MyResource lastResource = this.myResources.get(resourcesSize - 1);
				Integer nextResourceId = Integer.parseInt(lastResource.getResourceIdentifier()) + 1;
				this.resourceId.setText(nextResourceId.toString());
			}
			else {
				this.resourceId.setText("0");
			}
		}
		else {
			this.resourceId.setEditable(true);
			this.resourceId.setDisable(false);
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		column = new TableColumn<>("ID");
		column.setCellValueFactory(new PropertyValueFactory<>("resourceIdentifier"));
		column.setMaxWidth(400);
		
		column2 = new TableColumn<>("Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		resourcesTableView = new TableView<MyResource>();
		resourcesTableView.getColumns().add(column);
		resourcesTableView.getColumns().add(column2);
		resourcesTableView.setMinWidth(500);
		resourcesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		resourcesTableView.setMinHeight(300);
		
		tableViewContainer.getChildren().add(resourcesTableView);
	}
}
