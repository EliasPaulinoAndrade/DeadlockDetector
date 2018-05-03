package application.deadlock_detector;

import graph.GPEdge;
import graph.GPGraph;
import graph.GPNode;

public class OpGraph extends GPGraph{

	public Boolean addEdgeToNode(GPEdge<?> edge, GPNode<?> node) {
		if(edge instanceof OPEdge) {
			return super.addEdgeToNode(edge, node); 
		}
		return false;
	}
}
