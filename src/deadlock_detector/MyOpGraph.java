package deadlock_detector;

import graph.MyEdge;
import graph.MyGraph;
import graph.MyNode;

public class MyOpGraph extends MyGraph{

	public Boolean addEdgeToNode(MyEdge<?> edge, MyNode<?> node) {
		if(edge instanceof MyOpEdge) {
			return super.addEdgeToNode(edge, node); 
		}
		return false;
	}
}
