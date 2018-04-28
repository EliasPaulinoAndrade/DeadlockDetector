package application.graphDrawer;

public class MyPoint {
	private Double x;
	private Double y;
	public MyPoint(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public MyPoint(int x, int y) {
		super();
		this.x = (double) x;
		this.y = (double) y;
	}
	
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	
	
}
