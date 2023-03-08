package gameComponents;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball{

	//initialize fields
	public static final double BALLRADIUS = 25;
	public static final Paint BALLCOLOR = Color.web("#fa49ac");
	public int BALLXPOSITION = 192;
	public int BALLYPOSITION = 250;
	public static final int BALL_MIN_SPEED = 100;
    public static final int BALL_MAX_SPEED = 150;
    
//   private ImageView myView;
    private Point2D myVelocity;
    public Circle ball;
    private ImageView myView;
    private Random dice = new Random();
    
    
    public Ball (Image image) {
        myView = new ImageView(image);
        // make sure it stays a circle
        myView.setFitWidth(BALLRADIUS);
        myView.setFitHeight(BALLRADIUS);
        // make sure it stays within the bounds
        myView.setX(BALLXPOSITION);
        myView.setY(BALLYPOSITION);
        // turn speed into velocity that can be updated on bounces
        myVelocity = new Point2D(getRandomInRange(BALL_MIN_SPEED, BALL_MAX_SPEED),
                                 getRandomInRange(BALL_MIN_SPEED, BALL_MAX_SPEED));
    }
    
    public void setPosition(int X, int Y) {
    	myView.setX(X);
        myView.setY(Y);
    }
    
    public void moveBall(double elapsedTime) {
    	 myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
         myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }
    
    public void stayInWalls (double screenWidth, double screenHeight) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }
    
    public boolean dropsOff (double screenWidth, double screenHeight) {
    	if (myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
    		return true;
    	}
    	return false;
    }
       
    public Node getView () {
        return myView;
    }
    
    public void bounce() {
    	myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
    }
    
    private int getRandomInRange (int min, int max) {
        return min + dice.nextInt(max - min) + 1;
    }
    
    public void changeBallColor(Image nextLevelBall) {
    	myView.setImage(nextLevelBall);
    	myView.setFitWidth(BALLRADIUS);
        myView.setFitHeight(BALLRADIUS);
    }

    public void makeBallGoFaster() {
    	myVelocity = new Point2D(getRandomInRange(BALL_MIN_SPEED + 100, BALL_MIN_SPEED + 100),
                				 getRandomInRange(BALL_MIN_SPEED + 100, BALL_MIN_SPEED + 1000));
    }
}
