package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Board;
import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.HumanPlayer;
import main.ID;

public class Take3 extends MouseAdapter {

	private Game game;
	private Handler handler;
	private int[] picked; //bk, bl, g, r, w
	int numPicked;
	private HumanPlayer h;
	private Board b;
	
	public Take3(Game g, Handler handler) {
		game = g;
		this.handler = handler;
		picked = new int[]{0,0,0,0,0};
		numPicked = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 200, 200, 150, 150)) {
			if(picked[0] == 0) {
				picked[0] = 1;
				numPicked++;
			} else  {
				picked[0] = 0;
				numPicked--;
			}
		} else if(menuCollision(mx, my, 400, 200, 150, 150)) {
			if(picked[1] == 0) {
				picked[1] = 1;
				numPicked++;
			} else  {
				picked[1] = 0;
				numPicked--;
			}
		} else if(menuCollision(mx, my, 600, 200, 150, 150)) {
			if(picked[2] == 0) {
				picked[2] = 1;
				numPicked++;
			} else  {
				picked[2] = 0;
				numPicked--;
			}
		} else if(menuCollision(mx, my, 800, 200, 150, 150)) {
			if(picked[3] == 0) {
				picked[3] = 1;
				numPicked++;
			} else  {
				picked[3] = 0;
				numPicked--;
			}
		} else if(menuCollision(mx, my, 1000, 200, 150, 150)) {
			if(picked[4] == 0) {
				picked[4] = 1;
				numPicked++;
			} else  {
				picked[4] = 0;
				numPicked--;
			}
		} else if(menuCollision(mx, my, 300, 450, 250, 100)) {
			//SELECT
			if(isValidMove()) {
				for (int i = 0; i < 5; i++) {
					h.addCoins(i, picked[i]);
					b.removeCoins(i, picked[i]);
				}
				reset();
				game.removeMouseListener(game.threeView);
				game.addMouseListener(game.actionTaken);
				if (game.getMouseListeners().length != 1) {
					throw new IllegalStateException("More than one MouseListener");
				}
				game.gameState = STATE.ActionTaken;
			}	
		} else if (menuCollision(mx, my, 300, 600, 250, 100)) {
			//BACK
			reset();
			game.removeMouseListener(game.threeView);
			game.addMouseListener(game.humanTurnMenu);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.Game;
			
		} 
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean isValidMove() {
		for(GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) {
				h = (HumanPlayer) o;
			} else if (o.getID() == ID.Board) {
				b = (Board) o;
			}
		}
		
		if (numPicked != 3) return false;
		for(int i = 0; i < 5; i++) {
			if (picked[i] > b.getCoins()[i]) return false;
		}
		return true;
	}
	
	/** Detect whether or not the mouse at (mx, my) intersects the rectangle. */
	private boolean menuCollision(int mx, int my, int x, int y, int w, int h) {
		if(mx >= x && mx <= x + w) {
			if(my >= y && my <= y + h){
				return true;
			} 
			return false;
		}
		return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(200, 200, 150, 150);
		g.setFont(new Font("arial", 1, 90));
		g.drawString("Take 3", 700, 130);
		
		g.setColor(Color.BLUE);
		g.fillOval(400, 200, 150, 150);
		g.setColor(Color.GREEN);
		g.fillOval(600, 200, 150, 150);
		g.setColor(Color.RED);
		g.fillOval(800, 200, 150, 150);
		g.setColor(Color.WHITE);
		g.fillOval(1000, 200, 150, 150);
		
		g.setColor(Color.GRAY);
		g.fillOval(225, 225, 100, 100);
		g.fillOval(425, 225, 100, 100);
		g.fillOval(625, 225, 100, 100);
		g.fillOval(825, 225, 100, 100);
		g.fillOval(1025, 225, 100, 100);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 50));
		g.drawString(Integer.toString(picked[0]), 260, 290);
		g.drawString(Integer.toString(picked[1]), 460, 290);
		g.drawString(Integer.toString(picked[2]), 660, 290);
		g.drawString(Integer.toString(picked[3]), 860, 290);
		g.drawString(Integer.toString(picked[4]), 1060, 290);
		
		g.fillRect(300, 600, 250, 100);
		
		if(!isValidMove()) {
			g.setColor(Color.GRAY);
		}
		g.fillRect(300, 450, 250, 100);
		
		g.setColor(Color.BLACK);
		g.drawString("Confirm", 330, 515);
		g.drawString("Back", 365, 665);
	}

	private void reset() {
		picked = new int[]{0,0,0,0,0};
		numPicked = 0;
	}
}
