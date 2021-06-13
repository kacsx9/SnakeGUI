package Elements;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public abstract class Element {
	
	protected Ellipse2D img; //representative of the element on the board
	protected int x, y; //coordinates of the element (x  - vertical, y - horizontal)
	protected Color color; //color of the img

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//returning the char of the element
	public Ellipse2D getImg() {
		return img;
	}
	
	//returning co-ordinates of the element
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	//returning color of the element
	public Color getColor() {
		return this.color;
	}
	
	//updating coordinates of the element after move
	public void moveRight() {
		y++;
	}
	public void moveLeft() {
		y--;	
	}
	public void moveUp() {
		x--;
	}
	public void moveDown() {
		x++;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
