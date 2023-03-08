import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import gameComponents.Bat;
import gameComponents.Bricks;
import gameComponents.Ball;
import gameComponents.Player;
import gameComponents.TextDisplays;
import gameComponents.Blocker;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.File;


public class Level2Scene {
	public static final String TITLE = "Breakout";

	private Scene mySceneLevel2;

	public static final int width = 385;
	public static final int height = 500;
	public static final Paint BACKGROUND = Color.web("#feb4de");

	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public int BRICKCOUNT = 0;

	public static final String BALL_IMAGE = "resources/ball.gif";
	public static final String BRICK_IMAGE = "resources/brick.gif";
	public static final String BLOCKER_IMAGE = "resources/blocker.gif";
	public static final String CHANGED_BALL_COLOR_IMAGE = "resources/blueBall.gif";

	File file = new File("SCORES");
	private List<Bricks> myBricks;

	GamePlay gamer = new GamePlay();
	
	Bricks brick;
	Rectangle bat;
	Ball ball;
	Blocker blocker;
	Player player = new Player();
	Timeline animation;

	Text displayScore = new TextDisplays().displayScore();
	Text displayLives = new TextDisplays().displayLives();
	Text lostMessage = new TextDisplays().lostMessage();
	Text winMessage = new TextDisplays().winMessage();
	Text topScore = new TextDisplays().topScore();
	Text level = new TextDisplays().level();

	public Scene setUpLevel2(int width, int height, Paint background) {
		Group root = new Group();
		player = new Player();
		try {
			Image imageBall = new Image(new FileInputStream(BALL_IMAGE));
			ball = new Ball(imageBall);
			root.getChildren().add(ball.getView());
		}
		catch (FileNotFoundException e) {}
		bat = new Bat().createBat();

		int BRICKXPOSITION = 0;
		int BRICKYPOSITION = 0;

		myBricks = new ArrayList<>();   
		while (BRICKYPOSITION <= height/2.5) {
			while (BRICKXPOSITION <= 100 || BRICKXPOSITION >= 100) {
				try {
					Image imageBrick = new Image(new FileInputStream(BRICK_IMAGE));
					brick = new Bricks(imageBrick, BRICKXPOSITION, BRICKYPOSITION);
				}
				catch (FileNotFoundException e) {}
				myBricks.add(brick);
				root.getChildren().add(brick.getView()); 
				BRICKXPOSITION += brick.getWidth() + 5;   
			}
			this.brick.setPoints(brick.getPoints() - 1);
			BRICKYPOSITION += brick.getHeight() + 5; 
			BRICKXPOSITION = 0;
		}
		try {
			Image imageBlocker = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker = new Blocker(imageBlocker);
			root.getChildren().add(blocker.getView());
		}
		catch (FileNotFoundException e) {}
		
		
		if (player.getLevel() == 2) {
			try {
				Image imageBlocker = new Image(new FileInputStream(BLOCKER_IMAGE));
				blocker = new Blocker(imageBlocker);
				root.getChildren().add(blocker.getView());
			}
			catch (FileNotFoundException e) {}
		}
		
		displayScore.setText(displayScore.getText() + String.valueOf(player.getScore()));
		displayLives.setText(displayLives.getText() + String.valueOf(player.getLives()));
		level.setText(level.getText() + "1");
		
		root.getChildren().addAll(bat, displayScore, displayLives, lostMessage, winMessage, topScore, level);
		Scene scene = new Scene(root, width, height, background);
		//respond to input 
		scene.setOnKeyPressed(e -> new GamePlay().handleKeyInput(e.getCode(), bat));
		return scene;
	}
}
