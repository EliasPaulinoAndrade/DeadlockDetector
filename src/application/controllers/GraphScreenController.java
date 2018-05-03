package application.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.datasources.MyDeadlockGraphDataSource;
import application.delegates.MyDeadlockGraphDelegate;
import application.graphDrawer.GDGraphDrawer;
import deadlock_detector.MyOpSystem;
import deadlock_detector.MyProcess;
import deadlock_detector.MyProcessNode;
import deadlock_detector.MyResource;
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
	@FXML private TextField processId;
	@FXML private TextField processRestTime;
	@FXML private TextField processActiveTime;
	@FXML private CheckBox autoIdProcess;
	@FXML private AnchorPane graphContainer;
	
	private TableView<MyProcess> processTableView;
	private TableColumn<MyProcess, String> column;
	private TableColumn<MyProcess, String> column2;
	private TableColumn<MyProcess, String> column3;
	
	private MyDeadlockGraphDataSource graphDataSource;
	private MyDeadlockGraphDelegate graphDelegate;
	
	private GDGraphDrawer drawer;
	
	public void receiveData(List<MyResource> resources, Integer opSystemRestTime) {
		/*when the controller receives the data from the older screen it creates the graph with the data*/
		drawer = new GDGraphDrawer();
		
		MyOpSystem.setInstance(opSystemRestTime, resources, drawer);
		this.graphDataSource = new MyDeadlockGraphDataSource(MyOpSystem.shared().getGraph(), this.graphContainer);
		this.graphDelegate = new MyDeadlockGraphDelegate();
		
		drawer.setDataSource(graphDataSource);
		drawer.setDelegate(graphDelegate);
		
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
				Integer.parseInt(processRestTime.getText()), 
				Integer.parseInt(processActiveTime.getText())
				);
		MyProcessNode<MyProcess> processNode = new MyProcessNode<MyProcess>(process);
		process.setSelfNode(processNode);
		
		GPGraph graph = MyOpSystem.shared().getGraph();
		
		MyOpSystem.shared().getProcesses().add(processNode);
		processTableView.getItems().add(process);	
		graph.addNode(processNode);
		drawer.addNodeAt(graph.numberOfNodes() - 1);
		
		new Thread(process).start();
		
		if(this.autoIdProcess.isSelected()) {
			Integer nextResourceId = Integer.parseInt(this.processId.getText()) + 1;
			this.processId.setText(nextResourceId.toString());
		}
	
	}
	
	@FXML 
	private void handleCheckBoxAction(ActionEvent event) {	
		/*same autoincrementlogic*/
		
		if(autoIdProcess.isSelected()) {
			this.processId.setEditable(false);
			this.processId.setDisable(true);
			Integer processSize = MyOpSystem.shared().getProcesses().size();
			if(processSize > 0) {
				MyProcess lastProcess = MyOpSystem.shared().getProcesses().get(processSize - 1).getValue();
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
