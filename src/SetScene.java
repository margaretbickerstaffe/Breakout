import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gameComponents.Bat;
import gameComponents.Bricks;
import gameComponents.Ball;
import gameComponents.Player;
import gameComponents.TextDisplays;
import gameComponents.Blocker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.scene.text.Font;

public class SetScene extends Application {

	public static final String TITLE = "Breakout";
	private Scene myScene;
	private Scene levelOneScene;
	private Scene levelTwoScene;
	private Scene levelThreeScene;
	public static final int width = 385;
	public static final int height = 500;

	public static final Paint BACKGROUND = Color.web("#feb4de"); 
	public static final Paint LEVEL_ONE_BACKGROUND = Color.web("#011a52"); //blue
	public static final Paint LEVEL_TWO_BACKGROUND = Color.web("#f5e790"); //yellow
	public static final Paint LEVEL_THREE_BACKGROUND = Color.web("#8ae384"); //green

	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public int BRICKCOUNT = 0;

	public boolean switchLevel1 = true;
	public boolean switchLevel2 = true;
	public boolean jumpX = false;

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
	Blocker blocker2;
	Blocker blocker3;
	Player player = new Player();
	Timeline animation;

	Text displayScore = new TextDisplays().displayScore();
	Text displayLives = new TextDisplays().displayLives();
	Text lostMessage = new TextDisplays().lostMessage();
	Text winMessage = new TextDisplays().winMessage();
	Text topScore = new TextDisplays().topScore();
	Text level = new TextDisplays().level();
	
	public static void main(String[] args) {
    	
		launch(args);
    }
	
	@Override
	public void start (Stage stage) {
		// attach scene to the stage and display it
		myScene = setUp(width, height, LEVEL_ONE_BACKGROUND);
//		mySceneLevel2 = setUpLevel2(width, height, BACKGROUND);
		
		stage.setScene(myScene);
		stage.setTitle("Breakout");
		stage.show();
		
		// attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, stage));;
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public Scene setUp(int width, int height, Paint background) {
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
		while (BRICKYPOSITION <= height/5) {
			while (BRICKXPOSITION <= width) {
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
			Image imageBlocker2 = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker2 = new Blocker(imageBlocker2);
			root.getChildren().add(blocker2.getView());
		}
		catch (FileNotFoundException e) {}
		blocker.notVisible();
		blocker2.notVisible();
		displayScore.setText("SCORE: " + player.getScore());
		displayLives.setText("LIVES: " + player.getLives());
		level.setText(level.getText() + "1");
		
		root.getChildren().addAll(bat, displayScore, displayLives, lostMessage, winMessage, topScore, level);
		Scene scene = new Scene(root, width, height, background);
		//respond to input 
		scene.setOnKeyPressed(e -> new GamePlay().handleKeyInput(e.getCode(), bat));
		return scene;
	}

	private void step (double elapsedTime, Stage stage) {
		ball.moveBall(elapsedTime);
		for (Bricks brick : myBricks) {
			if (brick.getView().getBoundsInParent().intersects(ball.getView().getBoundsInParent()) && brick.visible()) {
				brick.notVisible();
				ball.bounce();
				BRICKCOUNT += 1;
				player.setScore(brick.getPoints() + player.getScore());
				displayScore.setText("SCORE: " + player.getScore());
				
				//once all bricks are cleared in level 1, go to level 2
				if(BRICKCOUNT >= 30 && switchLevel1) {
				//if(BRICKCOUNT >= 30 && switchLevel1) {
					levelTwoScene = levelTwo(width, height, LEVEL_TWO_BACKGROUND);
					stage.setScene(levelTwoScene);
					switchLevel1 = false;
				}
				
				//once all bricks are cleared in level 2, go to level 3
				if (BRICKCOUNT > 66 && switchLevel2) {
				//if(BRICKCOUNT >= 66 && switchLevel2) {
					levelThreeScene = levelThree(width, height, LEVEL_THREE_BACKGROUND);
					stage.setScene(levelThreeScene);
					switchLevel2 = false;
				}

				if(player.getScore() > 50) {
					try {
						Image newBallImage = new Image(new FileInputStream(CHANGED_BALL_COLOR_IMAGE));
						// Power Up - changes color of ball and makes ball move faster after score gets to 50
						ball.changeBallColor(newBallImage);
					//	ball.makeBallGoFaster();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} 
			}
		}
		
		if (blocker.getView().getBoundsInParent().intersects(ball.getView().getBoundsInParent()) && blocker.visible()) {
			ball.bounce();
		}
		if (blocker2.getView().getBoundsInParent().intersects(ball.getView().getBoundsInParent()) && blocker2.visible()) {
			ball.bounce();
		}

		if (bat.getBoundsInParent().intersects(ball.getView().getBoundsInParent())) {
			ball.bounce();
		}	

		ball.stayInWalls(myScene.getWidth(), myScene.getHeight());
		if (ball.dropsOff(myScene.getWidth(), myScene.getHeight())){
			player.lifeLost();
			displayLives.setText("LIVES: " + player.getLives());
			gamer.resetGame(ball);
		}

		if (gamer.hasWon(BRICKCOUNT)) {
			animation.stop();
			new TextDisplays().displayWinMessage(winMessage);
			
			// report a won message 
			// Reads in current score into scores file to check for high score
			addCurrentScoreToScoresFile(player.getScore(), file);
			// save a HIGH score to the scores file
			int highScore = 0;
			try {
				// Read the score file line by line
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while (line != null)                 
				{
					try {
						// Parse each line as an int
						int score = Integer.parseInt(line.trim()); 
						// Keep track of the largest score
						if (score > highScore)                       
						{ 
							highScore = score; 
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException ex) {
				System.err.println("Couldn't read in scores from file");
			}
		} 
		
		if(gamer.lostGame(player)) {
			animation.stop();
			addCurrentScoreToScoresFile(player.getScore(), file);
			new TextDisplays().displayLostMessage(lostMessage);
			//return a lost message
		}
	}


	private void addCurrentScoreToScoresFile(int score, File file) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
			output.newLine();
			output.append("" + score);
			output.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Scene levelTwo(int width, int height, Paint background) {
		Group root = new Group();
		player = new Player();
		try {
			Image imageBall = new Image(new FileInputStream(BALL_IMAGE));
			ball = new Ball(imageBall);
			root.getChildren().add(ball.getView());
		}
		catch (FileNotFoundException e) {}
		bat = new Bat().createBat();

		int BRICKXPOSITION = 390;
		int BRICKYPOSITION = 0;

		myBricks = new ArrayList<>();   
		while (BRICKYPOSITION <= height/2.5) {
			while ((BRICKXPOSITION >= 200)) {
				try {
					Image imageBrick = new Image(new FileInputStream(BRICK_IMAGE));
					brick = new Bricks(imageBrick, BRICKXPOSITION, BRICKYPOSITION);
				}
				catch (FileNotFoundException e) {}
				myBricks.add(brick);
				root.getChildren().add(brick.getView()); 
				BRICKXPOSITION -= brick.getWidth() + 5;  
			}
			this.brick.setPoints(brick.getPoints() - 1);
			BRICKXPOSITION = 390;
			BRICKYPOSITION += brick.getHeight() + 5; 
		}
		BRICKXPOSITION = 0;
		BRICKYPOSITION = 0;
		while (BRICKYPOSITION <= height/2.5) {
			while ((BRICKXPOSITION <= 100)) {
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
			BRICKXPOSITION = 0;
			BRICKYPOSITION += brick.getHeight() + 5; 
		}
		try {
			Image imageBlocker = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker = new Blocker(imageBlocker);
			root.getChildren().add(blocker.getView());
			Image imageBlocker2 = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker2 = new Blocker(imageBlocker2);
			root.getChildren().add(blocker2.getView());
		}
		catch (FileNotFoundException e) {}
		blocker2.notVisible();

		displayScore.setText("SCORE: " + player.getScore());
		displayLives.setText("LIVES: " + player.getLives());
		level.setText("LEVEL: " + "2");

		root.getChildren().addAll(bat, displayScore, displayLives, lostMessage, winMessage, topScore, level);
		Scene scene1 = new Scene(root, width, height, background);
		//respond to input 
		scene1.setOnKeyPressed(e -> new GamePlay().handleKeyInput(e.getCode(), bat));
		return scene1;
	}
	
	public Scene levelThree(int width, int height, Paint background) {
		Group root = new Group();
		player = new Player();
		try {
			Image imageBall = new Image(new FileInputStream(BALL_IMAGE));
			ball = new Ball(imageBall);
			root.getChildren().add(ball.getView());
		}
		catch (FileNotFoundException e) {}
		bat = new Bat().createBat();
		
		try {
			Image imageBlocker = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker = new Blocker(imageBlocker);
			blocker.setXPosition(0);
			blocker.setYPosition(250);
			root.getChildren().add(blocker.getView());
			Image imageBlocker2 = new Image(new FileInputStream(BLOCKER_IMAGE));
			blocker2 = new Blocker(imageBlocker2);
			blocker2.setXPosition(285);
			blocker2.setYPosition(250);
			root.getChildren().add(blocker2.getView());
		}
		catch (FileNotFoundException e) {}
		
		int BRICKXPOSITION = 0;
		int BRICKYPOSITION = 0;

		myBricks = new ArrayList<>();   
		while (BRICKYPOSITION <= height/5) {
			while (BRICKXPOSITION <= width) {
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
			BRICKYPOSITION += brick.getHeight() + 25; 
			BRICKXPOSITION = 0;
		}

		displayScore.setText("SCORE: " + player.getScore());
		displayLives.setText("LIVES: " + player.getLives());
		level.setText("LEVEL: " + "3");
		
		root.getChildren().addAll(bat, displayScore, displayLives, lostMessage, winMessage, topScore, level);
		Scene scene1 = new Scene(root, width, height, background);
		//respond to input 
		scene1.setOnKeyPressed(e -> new GamePlay().handleKeyInput(e.getCode(), bat));
		return scene1;
	}
}