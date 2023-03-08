package gameComponents;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Player{
	public int LIVES = 3;
	public int SCORE = 0;
	public int TOPSCORE;
	public int LEVEL = 1;
	
	public Player(){
		LIVES = 3;
	}

	public int getScore(){
		return SCORE;
	}

	public int getLives(){
		return LIVES;
	}

	public void setLives(int num) {
		LIVES = num;
	}

	public void setScore(int num) {
		SCORE = num;
	}

	public void setTopScore(int num) {
		TOPSCORE = num;
	}

	public void lifeLost() {
		LIVES = LIVES - 1;
	}

	public int getTopScore(){
		Scanner sc = null;
		String score = "";
		try {
			sc = new Scanner(new File("TOPSCORE"));
			// Check if there is another line of input
			while(sc.hasNextLine()){
				score = sc.nextLine();		        
			}
		} catch (IOException  exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}finally{
			if(sc != null)
				sc.close();
		}
		int topScore = Integer.parseInt(score);
		return(topScore);
	}
	
	public int levelUp(int level) {
		return level += 1; 
	}
	
	public int getLevel() {
		return LEVEL;
	}
}
