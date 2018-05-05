package application.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.datasource_implementations.OPGraphDrawerDataSource;
import application.delegate_implementations.OPGraphDrawerDelegate;
import application.op_graph.OPProcess;
import application.op_graph.OPProcessNode;
import application.op_graph.OPResource;
import application.op_graph.OPResourceNode;
import application.op_graph.OPSystem;
import graphDrawer.GDGraphDrawer;
import graph.GPGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GraphScreenController implements Initializable {
	@FXML private VBox processTableViewContainer; 
	@FXML private VBox resourcesTableViewContainer;
	@FXML private TextField processId;
	@FXML private TextField processRestTime;
	@FXML private TextField processActiveTime;
	@FXML private CheckBox autoIdProcess;
	@FXML private AnchorPane graphContainer;
	
	private TableView<OPProcess> processTableView;
	private TableColumn<OPProcess, String> processColumn;
	private TableColumn<OPProcess, String> processColumn2;
	private TableColumn<OPProcess, String> processColumn3;
	

	private TableView<OPResource> resourceTableView;
	private TableColumn<OPResource, String> resourceColumn;
	private TableColumn<OPResource, String> resourceColumn2;
	
	private OPGraphDrawerDataSource graphDataSource;
	private OPGraphDrawerDelegate graphDelegate;
	
	private GDGraphDrawer drawer;
	
	public void receiveData(List<OPResource> resources, Integer opSystemRestTime) {
		/*when the controller receives the data from the older screen it creates the graph with the data*/
		drawer = new GDGraphDrawer();
		
		OPSystem.setInstance(opSystemRestTime, resources, drawer);
		this.graphDataSource = new OPGraphDrawerDataSource(OPSystem.shared().getGraph(), this.graphContainer);
		this.graphDelegate = new OPGraphDrawerDelegate();
		
		drawer.setDataSource(graphDataSource);
		drawer.setDelegate(graphDelegate);
		
		graphContainer.getChildren().add(drawer);	
		
		for(OPResource resource : resources) {
			resourceTableView.getItems().add(resource);
		}

		graphContainer.setBottomAnchor(drawer, 0.0);
		graphContainer.setTopAnchor(drawer, 0.0);
		graphContainer.setLeftAnchor(drawer, 0.0);
		graphContainer.setRightAnchor(drawer, 0.0);	
		

		System.out.println(OPSystem.shared().getGraph());
	}
	
	public void viewDidLoad() {
		drawer.drawGraph();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*creates the tableview*/
		
		processColumn = new TableColumn<>("ID");
		processColumn.setCellValueFactory(new PropertyValueFactory<>("processIdentifier"));
		processColumn.setMaxWidth(900);
		
		processColumn2 = new TableColumn<>("Rest");
		processColumn2.setCellValueFactory(new PropertyValueFactory<>("restTime"));
		
		processColumn3 = new TableColumn<>("Active");
		processColumn3.setCellValueFactory(new PropertyValueFactory<>("activeTime"));
		
		processTableView = new TableView<OPProcess>();
		processTableView.getColumns().add(processColumn);
		processTableView.getColumns().add(processColumn2);
		processTableView.getColumns().add(processColumn3);
		processTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		processTableView.setMinHeight(250);
		processTableView.setEditable(true);
		processTableViewContainer.getChildren().add(processTableView);
		
		resourceColumn = new TableColumn<>("ID");
		resourceColumn.setCellValueFactory(new PropertyValueFactory<>("resourceIdentifier"));
		resourceColumn.setMaxWidth(900);
		
		resourceColumn2 = new TableColumn<>("Name");
		resourceColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		resourceTableView = new TableView<>();
		resourceTableView.getColumns().add(resourceColumn);
		resourceTableView.getColumns().add(resourceColumn2);
		resourceTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		resourceTableView.setMinHeight(250);
		resourcesTableViewContainer.getChildren().add(resourceTableView);
	}
	
	@FXML 
	private void handleSaveButtonAction(ActionEvent event) {
		/*when the save button is clicked a new process is created and added to the graph */
		
		OPProcess process = new OPProcess(
				processId.getText(), 
				Integer.parseInt(processRestTime.getText()), 
				Integer.parseInt(processActiveTime.getText())
				);
		
		OPSystem.shared().addProcess(process);
		
		GPGraph graph = OPSystem.shared().getGraph();
		
		processTableView.getItems().add(process);	
		drawer.addNodeAt(graph.numberOfNodes() - 1);
		
		new Thread(process).start();
		
		if(this.autoIdProcess.isSelected()) {
			Integer nextResourceId = Integer.parseInt(this.processId.getText()) + 1;
			this.processId.setText(nextResourceId.toString());
		}
	
	}
	
	@FXML 
	private void handleDeleteButtonAction(ActionEvent event) {
		/*when the save button is clicked a new process is created and added to the graph */
		
		System.out.println("DELETE");
	
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {	
		/*same autoincrementlogic*/
		
		if(autoIdProcess.isSelected()) {
			this.processId.setEditable(false);
			this.processId.setDisable(true);
			Integer processSize = OPSystem.shared().numberOfProcesses();
			if(processSize > 0) {
				OPProcess lastProcess = OPSystem.shared().getProcess(processSize - 1).getValue();
				Integer nextResourceId = Integer.parseInt(lastProcess.getProcessIdentifier()) + 1;
				this.processId.setText(nextResourceId.toString());
			}
			else {
				this.processId.setText("0");
			}
		}
		else {
			this.processId.setEditable(true);
			this.processId.setDisable(false);
		}
		
	}
}
