package Game;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;



public class Game extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public static final int SCREEN_WIDTH = 612;
	public static final int SCREEN_HEIGHT = 612;
	public static final int WIDTH = 30, HEIGHT = 30;
	public static final int INTERVAL = 300;
	public static final int BORDER = 5;

	public static final int GAME_WIDTH_RATIO = SCREEN_WIDTH / WIDTH;
	public static final int GAME_HEIGHT_RATIO = SCREEN_HEIGHT / HEIGHT;

	JFrame frame;
	Timer timer;
		
	static char direction = 'F';
	boolean isGameRunning = false;
	Board b;
	
	//Constructor
	public Game() {

		frame = new JFrame();

		b = new Board(this);
		frame.addKeyListener(this);
		frame.getContentPane().add(b, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snake");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		setFocusable(true);
        setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(150, this);
		timer.start();
	}
	
	public boolean checkIsGameRunning() {
		return isGameRunning;
	}


	//MAIN METHOD
	public static void main(String[] args) throws IOException, InterruptedException {
	
		new Game();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (Game.direction == 'D') {
			isGameRunning = true;
		}
		b.change(isGameRunning);
		if (isGameRunning) {
			
			switch(Game.direction) {
			case 'A':
				isGameRunning = b.moveLeft();
				break;
			case 'D':
				isGameRunning = b.moveRight();
				break;
			case 'S':
				isGameRunning = b.moveDown();
				break;
			case 'W':
				isGameRunning = b.moveUp();
				break;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D) {
			direction = 'D';
		}
		if(key == KeyEvent.VK_A) {
			direction = 'A';	
		}
		if(key == KeyEvent.VK_S) {
			direction = 'S';
		}
		if(key == KeyEvent.VK_W) {
			direction = 'W';
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	
	
}
