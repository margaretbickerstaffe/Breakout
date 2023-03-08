package gameComponents;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Bat{
	
	//initialize fields
    public int BATWIDTH = 150;
	public int BATHEIGHT = 30;
	public Paint BATCOLOR = Color.web("#db027e");
	
	//default constructors 
	public Rectangle createBat() {

		Rectangle bat = new Rectangle(110, 450, BATWIDTH, BATHEIGHT);

		bat.setFill(BATCOLOR);
		return bat;
	}
	
	public Rectangle createBat(int x, int y, int width, int height) {
		Rectangle bat = new Rectangle(x, y, BATWIDTH, BATHEIGHT);
		bat.setFill(BATCOLOR);
		return bat;
	}

}


