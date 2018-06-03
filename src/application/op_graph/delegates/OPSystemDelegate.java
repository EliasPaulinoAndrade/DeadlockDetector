package application.op_graph.delegates;


import application.op_graph.OPSystem;
import graph.GPNode;

/*the classes that implement this will be called when any event happens in the system
*/
public interface OPSystemDelegate {
	void systemAppendedEdgeToNode(OPSystem system, GPNode<?> gpNode);
	void systemWillRemoveLastEdgeFromNode(OPSystem system, GPNode<?> gpNode);
	void systemCheckedForDeadLocks(Boolean hasDeadLock);
}
