package Game;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import Elements.*;


public class Board extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Element[][] board; //game's board
	private ArrayDeque<Element> body; //snake's body 
	private boolean fruitEaten = true; //if the fruit's been eaten at the moment
	private Fruit fruit;
	boolean validation = true;
	boolean newGame = true;
	
	//Constructor
	public Board(Game g) {

		setBorder(BorderFactory.createLineBorder(Color.blue, 5, true));		
		setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
		setBackground(Color.black);
		setVisible(true);
		setFocusable(false);
		board = new Element[Game.HEIGHT][Game.WIDTH];
		for (Element[] elements : board) {
			Arrays.fill(elements, null);
		}

		body = new ArrayDeque<>();
		body.add(new Head(0,0));
		fruit = new Fruit(10,10);
		this.generateFruit();
		this.updateBoard();
	}
	
	public void change(boolean running) {
		if (running) {
			newGame = false;
			repaint();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (newGame) {
			//Main Menu
			g.setColor(Color.white);
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
			g.drawString("!!! SNAKE !!!", Game.SCREEN_HEIGHT/2 - 5*Game.GAME_HEIGHT_RATIO , Game.SCREEN_WIDTH/2- 6*Game.GAME_HEIGHT_RATIO);
			
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, 12));
			g.drawString("Instructions: ", Game.BORDER , Game.SCREEN_WIDTH/2);
			g.drawString("You have to collect as much fruits (red circles) as you can ", Game.BORDER , Game.SCREEN_WIDTH/2+Game.GAME_HEIGHT_RATIO);
			g.drawString("and not to touch the snake's body and the board's frame. ", Game.BORDER , Game.SCREEN_WIDTH/2+2*Game.GAME_HEIGHT_RATIO);
			g.drawString("W - move Up, S - move Down, A - move Left, D - move Right ", Game.BORDER , Game.SCREEN_WIDTH/2+4*Game.GAME_HEIGHT_RATIO);
			g.drawString("Press 'D' key to start the game ", Game.BORDER , Game.SCREEN_WIDTH/2+6*Game.GAME_HEIGHT_RATIO);
			g.drawString("!!! Good luck !!!", Game.SCREEN_HEIGHT/2 - 3*Game.GAME_HEIGHT_RATIO , Game.SCREEN_WIDTH/2+8*Game.GAME_HEIGHT_RATIO);
		}
		else {
			//Paint all the board
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) {				
					if (board[i][j] != null) {
						board[i][j].getImg().setFrame(j, i, 20, 20);
						g2d.setColor(board[i][j].getColor());
						g2d.draw(new Ellipse2D.Double(board[i][j].getY() * Game.GAME_WIDTH_RATIO+Game.BORDER,
								board[i][j].getX() * Game.GAME_HEIGHT_RATIO+Game.BORDER, 20, 20));
					}
				}
			}
			//When Game i over
			if (!validation) {
				g.setColor(Color.red);
				g.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
				g.drawString("!!! GAME OVER !!!", Game.SCREEN_HEIGHT/2 - 6*Game.GAME_HEIGHT_RATIO , Game.SCREEN_WIDTH/2);
				
				g.setColor(Color.white);
				g.setFont(new Font("TimesNewRoman", Font.PLAIN, 12));
				g.drawString("Score: "+(body.size()-1), Game.SCREEN_HEIGHT/2 - 2*Game.GAME_HEIGHT_RATIO , Game.SCREEN_WIDTH/2+8*Game.GAME_HEIGHT_RATIO);
			}	
		
		}
		
	}
	
	//Snake's moving methods
	public boolean moveRight() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.addLast( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );		
		body.getFirst().moveRight();
		validation = moveBody(previousX, previousY);
		if (validation) {
			
			this.updateBoard();
		}
		return validation;
	}
	public boolean moveLeft() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveLeft();
		validation = moveBody(previousX, previousY);
		if (validation) {
			
			this.updateBoard();
		}
		return validation;		
	}
	public boolean moveUp() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveUp();
		validation = moveBody(previousX, previousY);
		if (validation) {
			this.updateBoard();
		}
		return validation;
	}
	public boolean moveDown() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveDown();
		validation = moveBody(previousX, previousY);
		if (validation) {
			this.updateBoard();
		}
		return validation;
	}
	
	//Checking if the coordinates after move are valid and move the snake's tail
	private boolean moveBody(int previousX, int previousY) {
		if ( body.getFirst().getX() == fruit.getX() && body.getFirst().getY() == fruit.getY() ) {
			this.fruitEaten = true;
		}
		//if the coordinates are outside the board
		if ( body.getFirst().getX() < 0 || body.getFirst().getX() >= Game.WIDTH || body.getFirst().getY() < 0 || body.getFirst().getY() >= Game.HEIGHT ) {
			return false;
		}	
		else {		
			int tempX, tempY; 
			int i = 0;
			
			//if snake's head touches iltself's body
			tempX = body.getFirst().getX();
			tempY = body.getFirst().getY();
			boolean bodyTouched = false;
			for (Element e: body) {
				if ( i>0 && i+1 < body.size() ) {
					
					if (e.getX() == tempX && e.getY() == tempY) {
						bodyTouched = true;
						break;
					}
				}
				i++;
			}
			if (!bodyTouched ) {
				//moving snake's tail
				i = 0;
				for (Element e: body) {
					if ( i>0 && i+1 < body.size() ) {					
						tempX = e.getX();
						tempY = e.getY();
						e.setX(previousX);
						e.setY(previousY);
						previousX = tempX;
						previousY = tempY;	
					}
					i++;				
				}
				return true;
			}
			else {
				return false;
			}
			
		}
	}
	
	//Updating the Board after move
	private void updateBoard() {
				
		if(body.size()>1 && !fruitEaten) {
			board[body.getLast().getX()][body.getLast().getY()] = null;
			body.removeLast();			
		}
		else if (body.size()>1) {
			board[fruit.getX()][fruit.getY()] = null;
			this.generateFruit();
		}

		for (Element e: body) {
			board[e.getX()][e.getY()] = e;			
		}
		
		board[fruit.getX()][fruit.getY()] = fruit;
		this.fruitEaten = false;
	}
	
	private void generateFruit() {
		
		boolean isOnBody = true;
		int fruitX = 0, fruitY = 0;
		while (isOnBody) {
			isOnBody = false;
			fruitX = ThreadLocalRandom.current().nextInt(0, Game.WIDTH );
			fruitY = ThreadLocalRandom.current().nextInt(0, Game.HEIGHT );
			for (Element e: body) {
				if ( fruitX == e.getX() && fruitY == e.getY() ) {
					isOnBody = true;
					break;
				}
			}
		}
		this.fruit.setX(fruitX);
		this.fruit.setY(fruitY);
	}


}
