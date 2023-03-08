import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import gameComponents.Bricks;
import java.util.List;
import gameComponents.Ball;
import gameComponents.Player;

public class GamePlay {
	
	public static final int MOVER_SPEED = 15;
	
	Rectangle bat; 
	Rectangle brick;
	
	public Paint BRICKCOLOR = Color.web("#db027e");
	public static final Paint BACKGROUND = Color.web("#feb4de");
	
	public static final int BALLXDIRECTION = -2;
	public static final int BALLYDIRECTION = -2;
	
    public void handleKeyInput (KeyCode code, Rectangle bat) {
    	
        if (code == KeyCode.LEFT) {
            bat.setX(bat.getX() - MOVER_SPEED);
            if (bat.getX() <= 0) {
            	bat.setX(0);
            }
        }
        else if (code == KeyCode.RIGHT) {
            bat.setX(bat.getX() + MOVER_SPEED);
            if (bat.getX() >= 235) {
            	bat.setX(235);
            }
        }
    }
    public boolean handleEnterKey(KeyCode code) {
        if(code == KeyCode.ENTER) {
             return true;
        }
        return false;
    }
    
    public boolean hasWon(int brickcount) {
    	if (brickcount == 84) {
    		return true;
    	}
    	return false;
    }
    
    public void resetGame(Ball ball) {
    	ball.setPosition(192, 250);
    }
    
    public boolean lostGame(Player player) {
    	if (player.getLives() <= 0) {
    		return true;
    	}
    	return false;
    }
    
    public void setPoints(List<Bricks> bricks) {
    //	for (Bricks brick : myBricks) {
    }
    
    public boolean passedLevel1(int brickcount) {
    	if (brickcount == 84) {
    		return true;
    	}
    	return false;
    }
}
    //if ball and brick intersect, change brick color, rebound ball, add points

    
    //if ball and bat intersect, rebound ball
    
    //if ball falls below bat, -1 life 
    
    //if score > topscore, store topscore in text file 

