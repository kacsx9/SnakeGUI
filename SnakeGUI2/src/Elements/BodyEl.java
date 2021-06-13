package Elements;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import Game.*;

public class BodyEl extends Element {

	public BodyEl(int x, int y) {
		super(x,y);
		img = new Ellipse2D.Double(0, 0, Game.GAME_HEIGHT_RATIO, Game.GAME_HEIGHT_RATIO);
		color = Color.green;
	}
	
}
