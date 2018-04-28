package deadlock_detector;

import graph.MyEdge;
import graph.MyNode;
import graph.MyNodeValue;

/*its a node subclass that restricts the destination edges's nodes, destination nodes can never be the same type that it
 * */
public class MyProcessNode<NodeElement extends MyNodeValue> extends MyNode<NodeElement>{

	public MyProcessNode(NodeElement value, Integer id) {
		super(value, id);
	}
	
	@Override
	public Boolean addEdge(MyEdge<?> edge) {
		MyNode<?> destinationNode = edge.getDestinationVertex();
		if(destinationNode instanceof MyProcessNode<?>) {
			return false;
		}
				
		return super.addEdge(edge);
	}

}
