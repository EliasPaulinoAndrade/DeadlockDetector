package application;
	
import application.graphDrawer.MyGraphDrawer;
import application.graphDrawer.MyGraphDrawerDataSource;
import application.graphDrawer.MySize;
import deadlock_detector.MyProcess;
import deadlock_detector.MyProcessNode;
import deadlock_detector.MyResource;
import deadlock_detector.MyResourceNode;
import graph.MyEdge;
import graph.MyGraph;
import graph.MyNode;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class Main extends Application implements MyGraphDrawerDataSource {
	
	private MyGraph graph;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(setup(),600,600);
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
	
	private Pane setup() {
		graph = new MyGraph();
	
		MyNode<?> node = new MyResourceNode<>(new MyResource("A"), 0);
		MyNode<?> node2 = new MyResourceNode<>(new MyResource("B"), 1);
		MyNode<?> node3 = new MyResourceNode<>(new MyResource("C"), 2);
		MyNode<?> node4 = new MyProcessNode<>(new MyProcess("1"), 3);
		
		
		MyEdge<?> edge = new MyEdge<>(node, 10);
		//MyEdge<?> edge2 = new MyEdge<>(node, 5);
		MyEdge<?> edge3 = new MyEdge<>(node3, 17);
		//MyEdge<?> edge4 = new MyEdge<>(node, 3);
		
		graph.addNode(node);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		
		node.addEdge(edge);
		node.addEdge(edge3);
		node4.addEdge(edge);
		
		//node2.addEdge(edge2);
		
		//node3.addEdge(edge4);
		
		MyGraphDrawer btn = new MyGraphDrawer();
		btn.setDataSource(this);
		
		btn.drawGraph();
		
        Pane root = new Pane();
		
        root.getChildren().add(btn);
        
        return root;
	}

	@Override
	public Integer graphDrawerNumberOfNodes(MyGraphDrawer graphDrawer) {
		
		return this.graph.numberOfNodes();
	}

	@Override
	public Node graphDrawerNodeViewForNodeAtIndex(MyGraphDrawer graphDrawer, Integer index) {
		StackPane nodeView = new StackPane();
		MyNode<?> node = this.graph.getNodeAt(index);
		
		Circle nodeCircle = new Circle(30);
		nodeCircle.setFill(Color.WHITE);
		
		Text nodeText = new Text(node.getValue().getStringValue());
		
		nodeView.getChildren().addAll(nodeCircle, nodeText);
		
		return nodeView;
	}

	@Override
	public MySize graphDrawerGraphSize(MyGraphDrawer graphDrawer) {
	
		return new MySize(400, 400);
	}

	@Override
	public Color graphDrawerGraphColor(MyGraphDrawer graphDrawer) {
		
		return Color.BLACK;
	}

	@Override
	public MySize graphDrawerNodeMaxSize(MyGraphDrawer graphDrawer) {

		return new MySize(60, 60);
	}

	@Override
	public Integer graphDrawerNumberOfEdgesStartingFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer index) {

		return this.graph.getNodeAt(index).numberOfEdges();
	}

	@Override
	public Integer graphDrawerNodeDestinationFromEdgeAtIndexFromNodeAtIndex(MyGraphDrawer graphDrawer, Integer edgeIndex, Integer nodeIndex) {
		return this.graph.getNodeAt(nodeIndex).getEdgeAt(edgeIndex).getDestinationVertex().getId();
	}

	@Override
	public Color graphDrawerEdgesColor(MyGraphDrawer graphDrawer) {
		
		return Color.RED;
	}

	@Override
	public Double graphDrawerMinDistanceBetweenNodes(MyGraphDrawer graphDrawer) {
		
		return 50.0;
	}
}
