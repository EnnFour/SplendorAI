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

public class Take2 extends MouseAdapter {

	private Game game;
	private Handler handler;
	private int[] chosen; //bk, bl, g, r, w
	int numchosen;
	private HumanPlayer h;
	private Board b;
	
	/** Constructor which allows access to the game and the handler. */
	public Take2(Game g, Handler handler) {
		game = g;
		this.handler = handler;
		chosen = new int[]{0,0,0,0,0};
		numchosen = 0;
		for(GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) {
				h = (HumanPlayer) o;
			} else if (o.getID() == ID.Board) {
				b = (Board) o;
			}
		}
	}
	
	/** Determine which action to take based on what was clicked. */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 200, 200, 150, 150)) {
			if(chosen[0] == 0) {
				chosen[0] = 1;
				numchosen++;
			} else  {
				chosen[0] = 0;
				numchosen--;
			}
		} else if(menuCollision(mx, my, 400, 200, 150, 150)) {
			if(chosen[1] == 0) {
				chosen[1] = 1;
				numchosen++;
			} else  {
				chosen[1] = 0;
				numchosen--;
			}
		} else if(menuCollision(mx, my, 600, 200, 150, 150)) {
			if(chosen[2] == 0) {
				chosen[2] = 1;
				numchosen++;
			} else  {
				chosen[2] = 0;
				numchosen--;
			}
		} else if(menuCollision(mx, my, 800, 200, 150, 150)) {
			if(chosen[3] == 0) {
				chosen[3] = 1;
				numchosen++;
			} else  {
				chosen[3] = 0;
				numchosen--;
			}
		} else if(menuCollision(mx, my, 1000, 200, 150, 150)) {
			if(chosen[4] == 0) {
				chosen[4] = 1;
				numchosen++;
			} else  {
				chosen[4] = 0;
				numchosen--;
			}
		} else if(menuCollision(mx, my, 300, 450, 250, 100)) {
			//SELECT
			if(numchosen == 1) {	
				if(isValidMove()) {
					for (int i = 0; i < 5; i++) {
						h.addCoins(i, chosen[i] * 2);
						b.removeCoins(i, chosen[i] * 2);
					}
					reset();
					
					game.removeMouseListener(game.twoView);
					game.addMouseListener(game.actionTaken);
					if (game.getMouseListeners().length != 1) {
						throw new IllegalStateException("More than one MouseListener");
					}
					game.gameState = STATE.ActionTaken;
				}	

			}
		} else if (menuCollision(mx, my, 300, 600, 250, 100)) {
			//BACK
			reset();
			
			game.removeMouseListener(game.twoView);
			game.addMouseListener(game.humanTurnMenu);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.Game;
		} 
	}
	
	/** Determine if the current move is valid. */
	private boolean isValidMove() {
		for(GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) {
				h = (HumanPlayer) o;
			} else if (o.getID() == ID.Board) {
				b = (Board) o;
			}
		}
		
		if (numchosen != 1) return false;
		for(int i = 0; i < 5; i++) {
			if (chosen[i] * 2 > b.getCoins()[i] || (b.getCoins()[i] < 4 && chosen[i] == 1 )) return false;
		}
		return true;
	}
	
	public void mouseReleased(MouseEvent e) {
		
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
	
	/** Render the Menu. */
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(200, 200, 150, 150);
		g.setFont(new Font("arial", 1, 90));
		g.drawString("Take Same", 700, 130);
		
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
		g.drawString(Integer.toString(2 * chosen[0]), 260, 290);
		g.drawString(Integer.toString(2 * chosen[1]), 460, 290);
		g.drawString(Integer.toString(2 * chosen[2]), 660, 290);
		g.drawString(Integer.toString(2 * chosen[3]), 860, 290);
		g.drawString(Integer.toString(2 * chosen[4]), 1060, 290);
		
		g.fillRect(300, 600, 250, 100);
		
		if(!isValidMove()) {
			g.setColor(Color.GRAY);
		}
		g.fillRect(300, 450, 250, 100);
		
		g.setColor(Color.BLACK);
		g.drawString("Confirm", 330, 515);
		g.drawString("Back", 365, 665);
	}

	/** Clear all modifications to the menu. */
	private void reset() {
		chosen = new int[]{0,0,0,0,0};
		numchosen = 0;
	}
}
