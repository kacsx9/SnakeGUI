package Elements;

import java.awt.Color;
import Game.*;
import java.awt.geom.Ellipse2D;

public class Fruit extends Element {

	public Fruit(int x, int y) {
		super(x,y);
		img = new Ellipse2D.Double(0, 0, Game.GAME_HEIGHT_RATIO, Game.GAME_HEIGHT_RATIO);
		color = Color.red;
	}
}
