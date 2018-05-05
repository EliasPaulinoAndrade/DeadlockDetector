package application.op_graph;

import graph.GPEdge;
import graph.GPNode;

/*its a node subclass that restricts the destination edges's nodes, destination nodes can never be the same type that it
 * */
public class OPProcessNode<NodeElement extends OPProcess> extends GPNode<NodeElement>{

	public OPProcessNode(NodeElement value) {
		super(value);
	}
	
	@Override
	public Boolean addEdge(GPEdge<?> edge) {
		GPNode<?> destinationNode = edge.getDestinationVertex();
		if(destinationNode instanceof OPProcessNode<?>) {
			return false;
		}
				
		return super.addEdge(edge);
	}
}
