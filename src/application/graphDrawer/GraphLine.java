package application.graphDrawer;


import javafx.scene.layout.BorderPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GraphLine extends BorderPane {
	private Line mainLine;
	private Line arrowLine1;
	private Line arrowLine2;
	
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private double middleX;
	private double middleY;
	
	public GraphLine(double startX, double startY, double endX, double endY) {
		super();
		
		this.startX = 0;
		this.startY = 0;
		this.endX = endX - startX;
		this.endY = endY - startY;
		this.middleX = (this.startX + this.endX)/2;
		this.middleY = (this.startY + this.endY)/2;
		
		this.setTranslateX(startX);
		this.setTranslateY(startY);
		this.setWidth(this.endX);
		this.setHeight(this.endY);
		
		this.mainLine = new Line(0, 0, this.endX, this.endY);
		
		
		double angle = Math.atan2((this.endY - this.startY), (this.endX - this.startX)) - Math.PI / 2.0;
		double sin = Math.sin(angle);
        double cos = Math.cos(angle);
       
        
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + this.middleX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + this.middleY;
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + this.middleX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + this.middleY;
        
        
        this.arrowLine1 = new Line(this.middleX,this.middleY,x1,y1);
        this.arrowLine2 = new Line(this.middleX,this.middleY,x2,y2);
        
		this.getChildren().add(this.mainLine);
		this.getChildren().add(this.arrowLine1);
		this.getChildren().add(this.arrowLine2);
	}

	public void setStroke(Color color) {
		this.mainLine.setStroke(color);
		this.arrowLine1.setStroke(color);
		this.arrowLine2.setStroke(color);
	}
	
}
