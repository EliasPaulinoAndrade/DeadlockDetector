package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.ScreenConstants;
import application.op_graph.OPResource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreenController implements Initializable{
	@FXML private Button addButtton;
	@FXML private TextField resourceName;
	@FXML private TextField resourceId;
	@FXML private VBox tableViewContainer;
	@FXML private CheckBox autoIncrementCheck;
	@FXML private AnchorPane rootPane;
	@FXML private TextField opSystemRestTimeField;
	@FXML private AnchorPane resourcesChoiceBoxContainer;
	
	private ChoiceBox<String> resourcesChoiceBox;
	private TableView<OPResource> resourcesTableView;
	private TableColumn<OPResource, String> column;
	private TableColumn<OPResource, String> column2;
	
	private List<OPResource> myResources;
	
	public MainScreenController() {
		myResources = new ArrayList<>();
	}

	@FXML 
	private void handleSaveButtonAction(ActionEvent event) {
		/*when the save button is cliked, it creates a new resource and add it to the arraylist. If autoincrement is on:
		 * it increments the textfield id number 
		 * */
		OPResource resource  = new OPResource(
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
	private void handleStartButtonAction(ActionEvent event) throws IOException {
		/*the button start opens a new window by changing the scene with the new pane. It send the resources to the new controller
		 * using the method receiveData()*/
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("screens/GraphScreen.fxml"));
	    
		Node eventNode = (Node) event.getSource();
		Stage currentStage = (Stage) eventNode.getScene().getWindow();
		
		Scene nextScene = new Scene(loader.load());
		
		GraphScreenController controller = loader.getController();
		
		Integer opSystemRestTime = Integer.parseInt(opSystemRestTimeField.getText());
		
		controller.receiveData(myResources, opSystemRestTime);
		
		
		currentStage.setScene(nextScene);
		currentStage.show();
		
		controller.viewDidLoad();
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {
		/*if the autoincrement is set to on and if there is at least one resource saved: it gets the last resource id and use to calculate 
		 * the new resource id. if there is no resource:  it begins on 0*/
		
		if(autoIncrementCheck.isSelected()) {
			this.resourceId.setEditable(false);
			this.resourceId.setDisable(true);
			Integer resourcesSize = this.myResources.size();
			if(resourcesSize > 0) {
				OPResource lastResource = this.myResources.get(resourcesSize - 1);
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
	
	public void handleChoiceBoxAction(ActionEvent event) {
//		System.out.println("EI");
		this.resourceName.setText(resourcesChoiceBox.getValue());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*create the tableview and it fields*/
		
		column = new TableColumn<>("ID");
		column.setCellValueFactory(new PropertyValueFactory<>("resourceIdentifier"));
		column.setMaxWidth(400);
		
		column2 = new TableColumn<>("Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		resourcesTableView = new TableView<OPResource>();
		resourcesTableView.getColumns().add(column);
		resourcesTableView.getColumns().add(column2);
		resourcesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		resourcesTableView.setMinHeight(250);
		
		tableViewContainer.getChildren().add(resourcesTableView);
		
		resourcesChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(Collections.list(ScreenConstants.defaultResourcesNames.keys())));
		resourcesChoiceBoxContainer.getChildren().add(resourcesChoiceBox);
		resourcesChoiceBox.setOnAction(this::handleChoiceBoxAction);
	}
}
