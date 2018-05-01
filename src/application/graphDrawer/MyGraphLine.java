package application.graphDrawer;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*draw a graph edge a arrow*/

public class MyGraphLine extends BorderPane {
	private Line mainLine;
	private Line arrowLine1;
	private Line arrowLine2;
	
	private Double startX;
	private Double startY;
	private Double endX;
	private Double endY;
	private Double middleX;
	private Double middleY;
	
	
	public MyGraphLine(Double startX, Double startY, Double endX, Double endY) {
		super();
		
		this.startX = 0.0;
		this.startY = 0.0;
		this.endX = endX - startX;
		this.endY = endY - startY;
		this.middleX = (this.startX + this.endX)/2;
		this.middleY = (this.startY + this.endY)/2;
		
		this.setTranslateX(startX);
		this.setTranslateY(startY);
		this.setWidth(this.endX);
		this.setHeight(this.endY);
		
		this.arrowLine1 = new Line();
        this.arrowLine2 = new Line();
		this.mainLine = new Line();		
        
        this.arrowLine1.setOnMouseEntered(this.enteredChildren);
        this.arrowLine2.setOnMouseEntered(this.enteredChildren);
        this.mainLine.setOnMouseEntered(this.enteredChildren);
        
        this.arrowLine1.setOnMouseExited(this.exitedChildren);
        this.arrowLine2.setOnMouseExited(this.exitedChildren);
        this.mainLine.setOnMouseExited(this.exitedChildren);
          
		this.getChildren().add(this.mainLine);
		this.getChildren().add(this.arrowLine1);
		this.getChildren().add(this.arrowLine2);
		
		this.redraw();
	}
	
	private void redraw() {
		this.mainLine.setStartX(0);
		this.mainLine.setStartY(0);
		this.mainLine.setEndX(this.endX);
		this.mainLine.setEndY(this.endY);
		
		Double angle = Math.atan2((this.endY - this.startY), (this.endX - this.startX)) - Math.PI / 2.0;
		Double sin = Math.sin(angle);
		Double cos = Math.cos(angle);
       
        
		Double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + this.middleX;
		Double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + this.middleY;
		Double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + this.middleX;
		Double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + this.middleY;
        
		
		this.arrowLine1.setStartX(this.middleX);
		this.arrowLine1.setStartY(this.middleY);
		this.arrowLine1.setEndX(x1);
		this.arrowLine1.setEndY(y1);
		
		this.arrowLine2.setStartX(this.middleX);
		this.arrowLine2.setStartY(this.middleY);
		this.arrowLine2.setEndX(x2);
		this.arrowLine2.setEndY(y2);
	}
	
	public void setLinePosition(Double startX, Double startY, Double endX, Double endY) {
		this.startX = 0.0;
		this.startY = 0.0;
		this.endX = endX - startX;
		this.endY = endY - startY;
		this.middleX = (this.startX + this.endX)/2;
		this.middleY = (this.startY + this.endY)/2;
		
		this.setTranslateX(startX);
		this.setTranslateY(startY);
		this.setWidth(this.endX);
		this.setHeight(this.endY);
		
		redraw();
	}

	public void setStroke(Color color) {
		this.mainLine.setStroke(color);
		this.arrowLine1.setStroke(color);
		this.arrowLine2.setStroke(color);
	}

	public void setStrokeWidth(Double width) {
		this.mainLine.setStrokeWidth(width);
		this.arrowLine1.setStrokeWidth(width);
		this.arrowLine2.setStrokeWidth(width);
	}

	private EventHandler<Event> enteredChildren = new EventHandler<Event>() {
		
		@Override
		public void handle(Event event) {

			MyGraphLine.this.mainLine.setStrokeWidth(MyGraphLine.this.mainLine.getStrokeWidth() + 1);
			MyGraphLine.this.arrowLine1.setStrokeWidth(MyGraphLine.this.arrowLine1.getStrokeWidth() + 1);
			MyGraphLine.this.arrowLine2.setStrokeWidth(MyGraphLine.this.arrowLine2.getStrokeWidth() + 1);
			MyGraphLine.this.setOpacity(0.7);
			MyGraphLine.this.toFront();
		}
	};
	
	private EventHandler<Event> exitedChildren  = new EventHandler<Event>() {
		
		@Override
		public void handle(Event event) {

			MyGraphLine.this.mainLine.setStrokeWidth(MyGraphLine.this.mainLine.getStrokeWidth() - 1);
			MyGraphLine.this.arrowLine1.setStrokeWidth(MyGraphLine.this.arrowLine1.getStrokeWidth() - 1);
			MyGraphLine.this.arrowLine2.setStrokeWidth(MyGraphLine.this.arrowLine2.getStrokeWidth() - 1);
			MyGraphLine.this.setOpacity(1);
			MyGraphLine.this.toBack();
		}
	};
}
