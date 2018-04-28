package deadlock_detector;

import graph.MyEdge;
import graph.MyNode;
import graph.MyNodeValue;

public class MyResourceNode<NodeElement extends MyNodeValue> extends MyNode<NodeElement> {

	public MyResourceNode(NodeElement value, Integer id) {
		super(value, id);
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
