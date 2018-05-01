package application.graphDrawer;


/*its is a graphical representation of the edge*/
public class MyGraphicsEdge {
	MyGraphLine node;
	MyGraphicsNode startGraphicsNode;
	MyGraphicsNode endGraphicsNode;

	public MyGraphicsEdge(MyGraphLine node, MyGraphicsNode startGraphicsNode, MyGraphicsNode endGraphicsNode) {
		super();
		this.node = node;
		this.startGraphicsNode = startGraphicsNode;
		this.endGraphicsNode = endGraphicsNode;
	}

	public MyGraphLine getNode() {
		return node;
	}

	public void setNode(MyGraphLine node) {
		this.node = node;
	}

	public MyGraphicsNode getStartGraphicsNode() {
		return startGraphicsNode;
	}

	public void setStartGraphicsNode(MyGraphicsNode startGraphicsNode) {
		this.startGraphicsNode = startGraphicsNode;
	}

	public MyGraphicsNode getEndGraphicsNode() {
		return endGraphicsNode;
	}

	public void setEndGraphicsNode(MyGraphicsNode endGraphicsNode) {
		this.endGraphicsNode = endGraphicsNode;
	}
	
}
