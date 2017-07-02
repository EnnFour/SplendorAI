package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

public class MainMenu extends MouseAdapter{
	
	private Game game;
	private Handler handler;
	
	public MainMenu(Game g, Handler h) {
		game = g;
		handler = h;
	}
	
	/** Checks to see whether or not a Main Menu object has been clicked on. Resolves the event.*/
	public void mousePressed(MouseEvent e) {
		
			int mx = e.getX();
			int my = e.getY();
			
			if(menuCollision(mx, my, 355, 575, 250, 100)) {
				//vs 1 ai
				create1();
				game.removeMouseListener(game.mainMenu);
				game.addMouseListener(game.humanTurnMenu);
				game.gameState = STATE.Game;
			} else if(menuCollision(mx, my, 1315, 575, 250, 100)) {
				//vs 2 ai
				create2();
				game.removeMouseListener(game.mainMenu);
				game.addMouseListener(game.humanTurnMenu);
				game.gameState = STATE.Game;
			} else if (menuCollision(mx, my, 835, 750, 250, 100)) {
				//vs 3 ai
				create3();
				game.removeMouseListener(game.mainMenu);
				game.addMouseListener(game.humanTurnMenu);
				game.gameState = STATE.Game;
			}
	}
	
	public void mouseRelesaed(MouseEvent e) {
		
	}
	
	/** What actions the main menu performs 60 times per second */
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
	
	/**Render the main menu. */
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 50));
	
		//Splendor
		g.drawRect(550, 200, 810, 100);
		g.drawString("Splendor", 840, 275);
		
		g.setFont(new Font("arial", 1, 30));
		//Alex Welty
		g.drawRect(710, 325, 500, 100);
		g.drawString("Alex Welty", 875, 350);
		//Vs 1 AI
		g.drawRect(355, 575, 250, 100);
		g.drawString("VS 1 AI", 425, 635);
		//vs 2 ai
		g.drawRect(1315, 575, 250, 100);
		g.drawString("VS 2 AI", 1385, 635);
		//vs 3 ai
		g.drawRect(835, 750, 250, 100);
		g.drawString("VS 3 AI", 905, 810);
	}
	
	/**Initializes the handler for a 2 player game. */
	private void create1() {
		Board b = new Board(ID.Board, game, 2);
		HumanPlayer human = new HumanPlayer(ID.Player1, game);
		AIPlayer ai1 = new AIPlayer(ID.Player2, game, handler);
		
		handler.addObject(b);
		handler.addObject(human);
		handler.addObject(ai1);
		
		b.getPlayers().add(human);
		b.getPlayers().add(ai1);
		
	}
	
	/**Initializes the handler for a 3 player game. */
	private void create2() {
		Board b = new Board(ID.Board, game, 3);
		HumanPlayer human = new HumanPlayer(ID.Player1, game);
		AIPlayer ai1 = new AIPlayer(ID.Player2, game, handler);
		AIPlayer ai2 = new AIPlayer(ID.Player3, game, handler);
		
		handler.addObject(b);
		handler.addObject(human);
		handler.addObject(ai1);
		handler.addObject(ai2);
		
		b.getPlayers().add(human);
		b.getPlayers().add(ai1);
		b.getPlayers().add(ai2);
	}
	
	/**Initializes the handler for a 4 player game. */
	private void create3() {
		Board b = new Board(ID.Board, game, 4);
		HumanPlayer human = new HumanPlayer(ID.Player1, game);
		AIPlayer ai1 = new AIPlayer(ID.Player2, game, handler);
		AIPlayer ai2 = new AIPlayer(ID.Player3, game, handler);
		AIPlayer ai3 = new AIPlayer(ID.Player4, game, handler);
		
		handler.addObject(b);
		handler.addObject(human);
		handler.addObject(ai1);
		handler.addObject(ai2);
		handler.addObject(ai3);
		
		b.getPlayers().add(human);
		b.getPlayers().add(ai1);
		b.getPlayers().add(ai2);
		b.getPlayers().add(ai3);
	}

}
