package actions;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.AIPlayer;
import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.ID;

public class AITurn extends MouseAdapter{

	private Game game;
	private Handler handler;
	
	public AITurn(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}
	
	public void MousePressed(MouseEvent e) {
		
	}
	
	public void MouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		takeTurns();
		
		if (game.getTurnsRemaining() == 0 && game.getEndReached()) {
			game.removeMouseListener(game.aiView);
			game.addMouseListener(game.gameOverView);
			
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			
			game.gameState = STATE.GameOver;
		} else {
			game.removeMouseListener(game.aiView);
			game.addMouseListener(game.humanTurnMenu);
			
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			
			game.gameState = STATE.Game;
		}
	}
	
	private void takeTurns() {
		
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player2) ((AIPlayer) o).takeTurn();
		}
		
		if (handler.getObjects().size() == 4) {
			for (GameObject o : handler.getObjects()) {
				if (o.getID() == ID.Player3) ((AIPlayer) o).takeTurn();
			}
		}
		
		if (handler.getObjects().size() == 5) {
			for (GameObject o : handler.getObjects()) {
				if (o.getID() == ID.Player4) ((AIPlayer) o).takeTurn();
			}
		}
	}
	
}
