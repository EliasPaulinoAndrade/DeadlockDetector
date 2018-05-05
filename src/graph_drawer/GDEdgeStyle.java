package graph_drawer;

public enum GDEdgeStyle {
	MEDIUM_DOTTED(5d),
	LONG_DOTTED(7d),
	SHORT_DOTTED(3d),
	SOLID(-1d);
	
	private Double dotSize;
	
	GDEdgeStyle(Double dotSize) {
		this.dotSize = dotSize;
	}
	
	public Double dotSize() {
		return this.dotSize;
	}
}
