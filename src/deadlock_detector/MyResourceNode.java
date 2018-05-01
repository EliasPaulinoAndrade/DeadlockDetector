package deadlock_detector;

import graph.MyEdge;
import graph.MyNode;
import graph.MyNodeValue;

/*its a node subclass that restricts the destination edges's nodes, destination nodes can never be the same type that it.
 */

public class MyResourceNode<NodeElement extends MyResource> extends MyNode<NodeElement> {

	public MyResourceNode(NodeElement value) {
		super(value);
	}

	@Override
	public Boolean addEdge(MyEdge<?> edge) {
		MyNode<?> destinationNode = edge.getDestinationVertex();
		if(destinationNode instanceof MyResourceNode<?>) {
			return false;
		}
				
		return super.addEdge(edge);
	}
}
