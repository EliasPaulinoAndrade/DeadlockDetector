package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.datasources.MyDeadlockGraphDataSource;
import application.graphDrawer.MyGraphDrawer;
import deadlock_detector.MyProcess;
import deadlock_detector.MyProcessNode;
import deadlock_detector.MyResource;
import deadlock_detector.MyResourceNode;
import graph.MyEdge;
import graph.MyGraph;
import graph.MyNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	
	private List<MyResource> myResources;
	private List<MyProcess> myProcesses;
	private MyGraph graph;
	private MyDeadlockGraphDataSource graphDataSource;
	private MyGraphDrawer drawer;
	
	public GraphScreenController() {
		this.myProcesses = new ArrayList<>();
	}

	public void receiveData(List<MyResource> myResources) {
		/*when the controller receives the data from the older screen it creates the graph with the data*/
		
		this.myResources = myResources;	
		this.graph = new MyGraph();
		this.graphDataSource = new MyDeadlockGraphDataSource(this.graph, this.graphContainer);
		
		MyResourceNode<MyResource> myResourceNode;
		for (MyResource resource: myResources) {
			myResourceNode = new MyResourceNode<MyResource>(resource);
			graph.addNode(myResourceNode);
		}
		
		drawer = new MyGraphDrawer();
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
				Double.parseDouble(processActiveTime.getText())
				);
		
		myProcesses.add(process);
		processTableView.getItems().add(process);	
		graph.addNode(new MyProcessNode<MyProcess>(process));
		drawer.addNodeAt(graph.numberOfNodes() - 1);
		
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
		
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {	
		/*same autoincrementlogic*/
		
		if(autoIdProcess.isSelected()) {
			this.processId.setEditable(false);
			this.processId.setDisable(true);
			Integer processSize = this.myProcesses.size();
			if(processSize > 0) {
				MyProcess lastProcess = this.myProcesses.get(processSize - 1);
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
