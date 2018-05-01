package application.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.datasources.MyDeadlockGraphDataSource;
import application.graphDrawer.MyGraphDrawer;
import deadlock_detector.MyOpSystem;
import deadlock_detector.MyProcess;
import deadlock_detector.MyProcessNode;
import deadlock_detector.MyResource;
import deadlock_detector.MyResourceNode;
import graph.MyEdge;
import graph.MyGraph;
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
	@FXML private TextField processId;
	@FXML private TextField processRestTime;
	@FXML private TextField processActiveTime;
	@FXML private CheckBox autoIdProcess;
	@FXML private AnchorPane graphContainer;
	
	private TableView<MyProcess> processTableView;
	private TableColumn<MyProcess, String> column;
	private TableColumn<MyProcess, String> column2;
	private TableColumn<MyProcess, String> column3;
	
	private MyOpSystem operationalSystem;
	
	private MyDeadlockGraphDataSource graphDataSource;
	private MyGraphDrawer drawer;

	
	public void receiveData(List<MyResource> resources) {
		/*when the controller receives the data from the older screen it creates the graph with the data*/
		
		drawer = new MyGraphDrawer();
		
		this.operationalSystem = new MyOpSystem(5.0, resources, drawer);
		this.graphDataSource = new MyDeadlockGraphDataSource(this.operationalSystem.getGraph(), this.graphContainer);
		
		
		drawer.setDataSource(graphDataSource);
		
		graphContainer.getChildren().add(drawer);	

		graphContainer.setBottomAnchor(drawer, 0.0);
		graphContainer.setTopAnchor(drawer, 0.0);
		graphContainer.setLeftAnchor(drawer, 0.0);
		graphContainer.setRightAnchor(drawer, 0.0);	
	}
	
	public void viewDidLoad() {
		System.out.println(drawer.getLayoutBounds());	
		drawer.drawGraph();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*it creates the tableview*/
		
		column = new TableColumn<>("ID");
		column.setCellValueFactory(new PropertyValueFactory<>("processIdentifier"));
		column.setMaxWidth(900);
		
		column2 = new TableColumn<>("Rest");
		column2.setCellValueFactory(new PropertyValueFactory<>("restTime"));
		
		column3 = new TableColumn<>("Active");
		column3.setCellValueFactory(new PropertyValueFactory<>("activeTime"));
		
		processTableView = new TableView<MyProcess>();
		processTableView.getColumns().add(column);
		processTableView.getColumns().add(column2);
		processTableView.getColumns().add(column3);
		processTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		processTableView.setMinHeight(250);
		
		processTableViewContainer.getChildren().add(processTableView);
		
	}
	
	@FXML 
	private void handleSaveButtonAction(ActionEvent event) {
		/*when the save button is clicked a new process is created and added to the graph */
		
		MyProcess process = new MyProcess(
				processId.getText(), 
				Double.parseDouble(processRestTime.getText()), 
				Double.parseDouble(processActiveTime.getText()),
				operationalSystem
				);
		MyProcessNode<MyProcess> processNode = new MyProcessNode<MyProcess>(process);
		process.setSelfNode(processNode);
		
		MyGraph graph = operationalSystem.getGraph();
		
		operationalSystem.getProcesses().add(processNode);
		processTableView.getItems().add(process);	
		graph.addNode(processNode);
		drawer.addNodeAt(graph.numberOfNodes() - 1);
		
		new Thread(process).start();
		
		if(this.autoIdProcess.isSelected()) {
			Integer nextResourceId = Integer.parseInt(this.processId.getText()) + 1;
			this.processId.setText(nextResourceId.toString());
		}
		
//		MyProcessNode<MyProcess> processNode = (MyProcessNode<MyProcess>) graph.getNodeAt(1);
//		MyResourceNode<MyResource> resourceNode = (MyResourceNode<MyResource>) graph.getNodeAt(0);
//		MyEdge<?> edge = new MyEdge<>(resourceNode, null);
//		
//		graph.addEdgeToNode(edge, processNode);
//		drawer.addEdgeToNodeAt(1, 0);
//		
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {	
		/*same autoincrementlogic*/
		
		if(autoIdProcess.isSelected()) {
			this.processId.setEditable(false);
			this.processId.setDisable(true);
			Integer processSize = operationalSystem.getProcesses().size();
			if(processSize > 0) {
				MyProcess lastProcess = operationalSystem.getProcesses().get(processSize - 1).getValue();
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
