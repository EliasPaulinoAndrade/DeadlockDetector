package deadlock_detector;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class Main {

	public static void main(String[] args) {
		
		Graph<Integer, Integer> graph = new Graph<>();
		
		Node<Integer, Integer> node = new Node<Integer, Integer>(1, 0);
		Node<Integer, Integer> node2 = new Node<Integer, Integer>(5, 1);
		Node<Integer, Integer> node3 = new Node<Integer, Integer>(20, 2);
		
		Edge<Integer, Integer> edge = new Edge<Integer, Integer>(node2, 10);
		Edge<Integer, Integer> edge2 = new Edge<Integer, Integer>(node, 5);
		Edge<Integer, Integer> edge3 = new Edge<Integer, Integer>(node3, 17);
		Edge<Integer, Integer> edge4 = new Edge<Integer, Integer>(node, 3);
		
		graph.addNode(node);
		graph.addNode(node2);
		graph.addNode(node3);
		
		node.addEdge(edge);
		node.addEdge(edge3);
		
		node2.addEdge(edge2);
		
		node3.addEdge(edge4);
		
		System.out.println(graph);
		
	}
}
