package Elements;

import java.awt.Color;
import Game.*;
import java.awt.geom.Ellipse2D;

public class Head extends Element{

	public Head(int x, int y) {
		super(x,y);
		img = new Ellipse2D.Double(0, 0, Game.GAME_HEIGHT_RATIO, Game.GAME_HEIGHT_RATIO);
		color = Color.yellow;
	}
	
}
