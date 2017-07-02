package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

public class HumanTurnMenu extends MouseAdapter {

	private Game game;
	private Handler handler;
	
	public HumanTurnMenu(Game g, Handler h) {
		game = g;
		handler = h;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 1550, 40, 300, 100)) {
			//view opponent
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.oppView);
			game.gameState = STATE.ViewOpp;
		} else if(menuCollision(mx, my, 1550, 190, 300, 100)) {
			//take 3
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.threeView);
			game.gameState = STATE.Take3;
		} else if(menuCollision(mx, my, 1550, 340, 300, 100)) {
			//take 2
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.twoView);
			game.gameState = STATE.Take2;
		} else if(menuCollision(mx, my, 1550, 490, 300, 100)) {
			//buy
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.buyView);
			game.gameState = STATE.Buy;
		} else if(menuCollision(mx, my, 1550, 640, 300, 100)) {
			//reserve
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.buyView);
			game.gameState = STATE.Buy;
		} else if(menuCollision(mx, my, 1550, 790, 300, 100)) {
			//view reserved
			game.removeMouseListener(game.humanTurnMenu);
			game.addMouseListener(game.reservesView);
			game.gameState = STATE.Reserves;
		} else if(menuCollision(mx, my, 1550, 940, 300, 100)) {
			//main menu
			game.removeMouseListener(game.humanTurnMenu);
			game.gameState = STATE.Reset;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		if (game.getEndReached() && game.getTurnsRemaining() == 0) {
			gameOver();
			return;
		}
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 30));

		g.fillRect(1550, 40, 300, 100);
		g.fillRect(1550, 190, 300, 100);
		g.fillRect(1550, 340, 300, 100);
		g.fillRect(1550, 490, 300, 100);
		g.fillRect(1550, 640, 300, 100);
		g.fillRect(1550, 790, 300, 100);
		g.fillRect(1550, 940, 300, 100);
		
		g.setColor(Color.BLACK);
		g.drawString("View Opponent", 1590, 100);
		g.drawString("Take 3", 1650, 250);
		g.drawString("Take 2", 1650, 400);
		g.drawString("Buy", 1675, 550);
		g.drawString("Reserve", 1650, 700);
		g.drawString("View Reserved", 1600, 850);
		g.drawString("Main Menu", 1625, 1000);
		
	}
	
	private boolean menuCollision(int mx, int  my, int x, int y, int w, int h) {
		if (mx >= x && mx <= x + w) {
			if (my >= y && my <= y + h) {
				return true;
			}
			
			return false;
		}
		return false;
	}
	
	private void gameOver() {
		game.removeMouseListener(game.humanTurnMenu);
		game.addMouseListener(game.gameOverView);
		game.gameState = STATE.GameOver;
	}
	
}
