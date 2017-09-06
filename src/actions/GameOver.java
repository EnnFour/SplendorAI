package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.ID;
import main.Player;

public class GameOver extends MouseAdapter {

	private Game game;
	private Handler handler;
	private Player winner;
	
	public GameOver(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		
	}
	
	/** Perform actions based on what was clicked. */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (menuCollision(mx, my, 740, 700, 350, 150)) {
			reset();
			game.removeMouseListener(game.gameOverView);
			game.addMouseListener(game.mainMenu);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.Main;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	/** Render the Game Over Menu. */
	public void render(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 90));
		g.drawString("Game Over!", 660, 100);
		g.setFont(new Font("arial", 1, 60));
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) {
				String s = "You had " + Integer.toString(((Player) o).getVictoryPoints()) + " Victory Points";
				g.drawString(s, 550, 200);
			} else if (o.getID() == ID.Player2) {
				String s = "Player 2 had " + Integer.toString(((Player) o).getVictoryPoints()) + " Victory Points";
				g.drawString(s, 500, 280);
			} else if (o.getID() == ID.Player3) {
				String s = "Player 3 had " + Integer.toString(((Player) o).getVictoryPoints()) + " Victory Points";
				g.drawString(s, 500, 360);
			} else if (o.getID() == ID.Player4) {
				String s = "Player 4 had " + Integer.toString(((Player) o).getVictoryPoints()) + " Victory Points";
				g.drawString(s, 500, 440);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(740, 700, 350, 150);
		g.setColor(Color.BLACK);
		g.drawString("Main Menu", 760, 790);
		
	}
	
	private void reset() {
		
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

}
