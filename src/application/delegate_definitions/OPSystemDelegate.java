package application.delegate_definitions;


import application.op_graph.OPSystem;
import graph.GPNode;

public interface OPSystemDelegate {
	void systemAppendedEdgeToNode(OPSystem system, GPNode<?> gpNode);
	void systemWillRemoveLastEdgeFromNode(OPSystem system, GPNode<?> gpNode);
	
}
