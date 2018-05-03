package deadlock_detector;

import graph.GPEdge;
import graph.GPNode;
import graph.GPNodeValue;

/*its a node subclass that restricts the destination edges's nodes, destination nodes can never be the same type that it.
 */

public class MyResourceNode<NodeElement extends MyResource> extends GPNode<NodeElement>{

	public MyResourceNode(NodeElement value) {
		super(value);
	}

	@Override
	public Boolean addEdge(GPEdge<?> edge) {
		GPNode<?> destinationNode = edge.getDestinationVertex();
		if(destinationNode instanceof MyResourceNode<?>) {
			return false;
		}
				
		return super.addEdge(edge);
	}
}
