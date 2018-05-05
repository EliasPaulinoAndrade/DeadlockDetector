package graph_drawer;


/*its is a graphical representation of the edge*/
public class GDGraphicsEdge {
	GDGraphLine node;
	GDGraphicsNode startGraphicsNode;
	GDGraphicsNode endGraphicsNode;

	public GDGraphicsEdge(GDGraphLine node, GDGraphicsNode startGraphicsNode, GDGraphicsNode endGraphicsNode) {
		super();
		this.node = node;
		this.startGraphicsNode = startGraphicsNode;
		this.endGraphicsNode = endGraphicsNode;
	}

	public GDGraphLine getNode() {
		return node;
	}

	public void setNode(GDGraphLine node) {
		this.node = node;
	}

	public GDGraphicsNode getStartGraphicsNode() {
		return startGraphicsNode;
	}

	public void setStartGraphicsNode(GDGraphicsNode startGraphicsNode) {
		this.startGraphicsNode = startGraphicsNode;
	}

	public GDGraphicsNode getEndGraphicsNode() {
		return endGraphicsNode;
	}

	public void setEndGraphicsNode(GDGraphicsNode endGraphicsNode) {
		this.endGraphicsNode = endGraphicsNode;
	}
	
}
