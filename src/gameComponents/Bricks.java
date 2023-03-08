package gameComponents;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks{
	
	//initialize fields
	public int BRICKWIDTH = 60;
	public int BRICKHEIGHT = 20;
	public int BRICKXPOSITION = 0;
	public int BRICKYPOSITION = 0;
	public int POINTVALUE = 9;
	private ImageView myView;
	
	public Bricks (Image image) {
        myView = new ImageView(image);
        // make sure it stays a circle
        myView.setFitWidth(BRICKWIDTH);
        myView.setFitHeight(BRICKHEIGHT);
        // make sure it stays within the bounds
        myView.setX(BRICKXPOSITION);
        myView.setY(BRICKYPOSITION);
    }
	
	public Bricks(Image image, int x, int y) {
        myView = new ImageView(image);
        // make sure it stays a circle
        myView.setFitWidth(BRICKWIDTH);
        myView.setFitHeight(BRICKHEIGHT);
        // make sure it stays within the bounds
        myView.setX(x);
        myView.setY(y);
    }
	
	public boolean visible() {
		return myView.isVisible();
	}
	public void notVisible() {
		myView.setVisible(false);
	}
	public void setVisible() {
		myView.setVisible(true);
	}
	
	public double getWidth() {
		return myView.getFitWidth();
	}
	
	public double getHeight() {
		return myView.getFitHeight();
	}
	
	public void setX(int x) {
		myView.setFitWidth(x);
	}
	
	public double getXPosition() {
		return myView.getX();
	}
	
	public void setY(int y) {
		myView.setFitHeight(y);
	}
	
	public double getYPosition() {
		return myView.getY();
	}
	
	public Node getView () {
	    return myView;
	}
	
	public int getPoints() {
		return POINTVALUE;
	}
	
	public void setPoints(int num) {
		POINTVALUE = num;
	}
	//default constructors 
	//public Rectangle createBrick() {
	//	Rectangle brick = new Rectangle(BRICKWIDTH, BRICKHEIGHT);
	//	brick.setFill(BRICKCOLOR);
	//	return brick;
//	}
	
	//public Rectangle createBrick(int x, int y) {
	//	Rectangle brick = new Rectangle(x, y, BRICKWIDTH, BRICKHEIGHT);
	//	brick.setFill(BRICKCOLOR);
	//	return brick;
	//}
	
}