package application.graphDrawer;

public class MySize {
	private Double width;
	private Double height;
	
	public MySize(Double width, Double height) {
		super();
		this.width = width;
		this.height = height;
	}

	public MySize(int width, int height) {
		this.width = (double) width;
		this.height = (double) height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}	
}
