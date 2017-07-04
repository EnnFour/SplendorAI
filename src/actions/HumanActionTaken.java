package actions;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import main.Board;
import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.HumanPlayer;
import main.ID;
import main.Noble;

public class HumanActionTaken extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private HumanPlayer h;
	private boolean toBank;
	private boolean payment;
	private boolean nobleVisit;
	private Board b;
	
	public HumanActionTaken(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		payment = false;
		toBank = false;
		nobleVisit = false;
	}

	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		for(GameObject o : handler.getObjects()) {
			if (o.getID() ==ID.Player1) h = (HumanPlayer) o;
			else if (o.getID() == ID.Board) b = (Board) o;
		}
		
		if (h.getNumCoins() > 10) {
			toBank = true;
		} else if (!(Arrays.equals(h.getDebt(), new int[]{0,0,0,0,0}))) {
			payment = true;
		} else if (noblesWillVisit()) {
			nobleVisit = true;
		}
		
		if(toBank) {
			reset();
			game.removeMouseListener(game.actionTaken);
			game.addMouseListener(game.toBankView);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.ToBank;
		} else if (payment) {
			reset();
			game.removeMouseListener(game.actionTaken);
			game.addMouseListener(game.purchaseView);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.Purchase;
		} else if (nobleVisit){
			reset();
			game.removeMouseListener(game.actionTaken);
			game.addMouseListener(game.nobleView);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.Noble;
		} else {
			reset();
			game.removeMouseListener(game.actionTaken);
			game.addMouseListener(game.aiView);
			if (game.getMouseListeners().length != 1) {
				throw new IllegalStateException("More than one MouseListener");
			}
			game.gameState = STATE.AITurn;
		}
		
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
	
	private void reset() {
		if (h.getVictoryPoints() >= 15) {
			game.setEndReached(true);
			game.setTurnsRemaining(handler.getObjects().size() - 1);
		}
		if (game.getEndReached()) {
			game.setTurnsRemaining(game.getTurnsRemaining() - 1);
		}
		payment = false;
		toBank = false;
		nobleVisit = false;
	}
	
	private boolean noblesWillVisit() {
		for (Noble n : b.getNobles()) {
			int[] cost = n.getCost();
			int[] owned = h.getCardTypes();
			int[] temp = new int[5];
			for(int i = 0; i < 5; i++) {
				temp[i] = owned[i] - cost[i];
			}
			if (temp[0] >= 0 && temp[1] >= 0 && temp[2] >= 0 && temp[3] >= 0 && temp[4] >= 0) return true;
		}
		return false;
	}

}
