package deadlock_detector;

import graph.GPEdge;
import graph.GPGraph;
import graph.GPNode;

public class MyOpGraph extends GPGraph{

	public Boolean addEdgeToNode(GPEdge<?> edge, GPNode<?> node) {
		if(edge instanceof MyOpEdge) {
			return super.addEdgeToNode(edge, node); 
		}
		return false;
	}
}
