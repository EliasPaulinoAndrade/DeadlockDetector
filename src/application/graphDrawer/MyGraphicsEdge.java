package application.graphDrawer;

import javafx.scene.Node;

public class MyGraphicsEdge {
	MyGraphLine node;

	public MyGraphicsEdge(MyGraphLine node) {
		super();
		this.node = node;
	}

	public MyGraphLine getNode() {
		return node;
	}

	public void setNode(MyGraphLine node) {
		this.node = node;
	}
	
}
