package application;
	
import application.graphDrawer.GraphDrawer;
import application.graphDrawer.GraphDrawerDataSource;
import application.graphDrawer.MySize;
import graph.MyEdge;
import graph.MyGraph;
import graph.MyNode;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Main extends Application implements GraphDrawerDataSource {
	
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
		MyNode<?> node = new MyNode<>(1, 0);
		MyNode<?> node2 = new MyNode<>(5, 1);
		MyNode<?> node3 = new MyNode<>(20, 2);
		
		MyEdge<?> edge = new MyEdge<>(node2, 10);
		MyEdge<?> edge2 = new MyEdge<>(node, 5);
		MyEdge<?> edge3 = new MyEdge<>(node3, 17);
		MyEdge<?> edge4 = new MyEdge<>(node, 3);
		
		graph.addNode(node);
		graph.addNode(node2);
		graph.addNode(node3);
		
		node.addEdge(edge);
		node.addEdge(edge3);
		
		node2.addEdge(edge2);
		
		node3.addEdge(edge4);
		
		
		GraphDrawer btn = new GraphDrawer();
		btn.setDataSource(this);
		
		btn.drawGraphNodes();
		
        Pane root = new Pane();
		
        root.getChildren().add(btn);
        
        return root;
	}

	@Override
	public Integer graphDrawerNumberOfNodes(GraphDrawer graphDrawer) {
		
		return this.graph.numberOfNodes();
	}

	@Override
	public Node graphDrawerNodeViewForIndex(GraphDrawer graphDrawer, Integer index) {
		
		return null;
	}

	@Override
	public MySize graphDrawerGraphSize(GraphDrawer graphDrawer) {
	
		return new MySize(400, 400);
	}

	@Override
	public Color graphDrawerGraphColor(GraphDrawer graphDrawer) {
		
		return Color.BLACK;
	}
}
