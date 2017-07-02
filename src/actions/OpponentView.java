package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.AIPlayer;
import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.ID;

public class OpponentView extends MouseAdapter {

	private Game game;
	private Handler handler;
	private int offset;
	
	public OpponentView(Game g, Handler handler) {
		game = g;
		this.handler = handler;
		offset = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		if (menuCollision(e.getX(), e.getY(), 1550, 900, 250, 100)) {
			game.removeMouseListener(game.oppView);
			game.addMouseListener(game.humanTurnMenu);
			game.gameState = STATE.Game;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
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
	
	public void render(Graphics g) {
		for(GameObject o : handler.getObjects()) {
			if (o.getID() != ID.Player1 && o.getID() != ID.Board) {
				renderPlayer(o, g);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(1550, 900, 250, 100);
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 60));
		g.drawString("Back", 1600, 970);
		
	}
	
	private void renderPlayer(GameObject o, Graphics g) {
		AIPlayer p = (AIPlayer) o;
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 60));
		
		if(p.getID() == ID.Player2) {
			offset = 0;
			g.drawString("Player 2", 1550, 180);
		} else if (p.getID() == ID.Player3) {
			offset = 300;
			g.drawString("Player 3", 1550, 180 + offset);
		} else if (p.getID() == ID.Player4) {
			offset = 600;
			g.drawString("Player 4", 1550, 180 + offset);
		}
		
		int[] cardTypes = p.getCardTypes();
		int[] coins = p.getCoins();
		
		g.fillRect(100, 100 + offset, 120, 160);
		g.fillOval(880, 100 + offset, 70,70);
		
		g.setColor(Color.BLUE);
		g.fillRect(250, 100 + offset, 120, 160);
		g.fillOval(960, 100 + offset, 70, 70);
		
		g.setColor(Color.RED);
		g.fillRect(400, 100 + offset, 120, 160);
		g.fillOval(1040, 100 + offset, 70, 70);
		
		g.setColor(Color.GREEN);
		g.fillRect(550, 100 + offset, 120, 160);
		g.fillOval(880, 180 + offset, 70, 70);
		
		g.setColor(Color.WHITE);
		g.fillRect(700, 100 + offset, 120, 160);
		g.fillOval(960, 180 + offset, 70, 70);
		
		g.setColor(Color.ORANGE);
		g.fillOval(1040, 180 + offset, 70, 70);
		
		g.setColor(Color.GRAY);
		g.fillRect(120, 120 + offset, 80, 120);
		g.fillRect(270, 120 + offset, 80, 120);
		g.fillRect(420, 120 + offset, 80, 120);
		g.fillRect(570, 120 + offset, 80, 120);
		g.fillRect(720, 120 + offset, 80, 120);
		
		g.fillOval(890, 110 + offset, 50, 50);
		g.fillOval(970, 110 + offset, 50, 50);
		g.fillOval(1050, 110 + offset, 50, 50);
		g.fillOval(890, 190 + offset, 50, 50);
		g.fillOval(970, 190 + offset, 50, 50);
		g.fillOval(1050, 190 + offset, 50, 50);
		
		g.fillRect(1250, 100 + offset, 150, 150);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 40));
		g.drawString(Integer.toString(cardTypes[0]), 150, 180 + offset);
		g.drawString(Integer.toString(cardTypes[1]), 300, 180 + offset);
		g.drawString(Integer.toString(cardTypes[3]), 450, 180 + offset);
		g.drawString(Integer.toString(cardTypes[2]), 600, 180 + offset);
		g.drawString(Integer.toString(cardTypes[4]), 750, 180 + offset);
		
		g.setFont(new Font("arial", 1, 20));
		g.drawString(Integer.toString(coins[0]), 910, 140 + offset);
		g.drawString(Integer.toString(coins[1]), 990, 140 + offset);
		g.drawString(Integer.toString(coins[3]), 1070, 140 + offset);
		g.drawString(Integer.toString(coins[2]), 910, 220 + offset);
		g.drawString(Integer.toString(coins[4]), 990, 220 + offset);
		g.drawString(Integer.toString(coins[5]), 1070, 220 + offset);
		g.drawString("NOBLES: " + p.getNobles(), 1265, 150 + offset);
		g.drawString("VPS: " + p.getVictoryPoints(), 1265, 175 + offset);
	}

}
