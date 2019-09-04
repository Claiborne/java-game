package javagame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics graphics) {
		// Background
		graphics.setColor(Color.black);
		graphics.fillRect(1, 1, 692, 592);
		
		// Draw map
		map.draw((Graphics2D)graphics);
		
		// Borders
		graphics.setColor(Color.yellow);
		graphics.fillRect(0, 0, 3, 592);
		graphics.fillRect(0, 0, 692, 3);
		graphics.fillRect(691, 0, 3, 592);
		
		// Score
		graphics.setColor(Color.white);
		graphics.setFont(new Font("serif", Font.BOLD, 25));
		graphics.drawString("" + score, 590, 30);
		
		// Paddle
		graphics.setColor(Color.green);
		graphics.fillRect(playerX, 550, 100, 8);
		
		// Ball
		graphics.setColor(Color.yellow);
		graphics.fillOval(ballposX, ballposY, 20, 20);
		
		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			graphics.setColor(Color.GREEN);
			graphics.setFont(new Font("serif", Font.BOLD, 30));
			graphics.drawString("You won!", 190, 300);
			
			graphics.setFont(new Font("serif", Font.BOLD, 20));
			graphics.drawString("Press Enter to replay", 230, 350);
		}
		
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			graphics.setColor(Color.RED);
			graphics.setFont(new Font("serif", Font.BOLD, 30));
			graphics.drawString("Game Over! Score: " + score, 190, 300);
			
			graphics.setFont(new Font("serif", Font.BOLD, 20));
			graphics.drawString("Press Enter to restart", 230, 350);
		}
		
		graphics.dispose();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			
			Rectangle ballHitBox = new Rectangle(ballposX, ballposY, 20, 20);
			Rectangle paddleHitBox = new Rectangle(playerX, 550, 100, 8);
			if (ballHitBox.intersects(paddleHitBox)) {
				ballYdir = -ballYdir;
			}
			
		 A:	for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle brickHitBox = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						if (ballHitBox.intersects(brickHitBox)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickHitBox.x || ballposX +1 >= brickHitBox.x + brickHitBox.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if (ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if (ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}	
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
}
