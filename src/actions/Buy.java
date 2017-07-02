package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Board;
import main.Game;
import main.GameObject;
import main.Handler;
import main.HumanPlayer;
import main.ID;
import main.Game.STATE;

public class Buy extends MouseAdapter {

	private Game game;
	private Handler handler;
	private Board b;
	private boolean[][] cards;
	private HumanPlayer h;
	private int numSelected;
	private int cost[];
	
	public Buy(Game g, Handler handler) {
		game = g;
		this.handler = handler;
		cards = new boolean[3][4];
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				cards[i][j] = false;
			}
		}
		numSelected = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 360, 100, 120, 160)) {
			if(b.getThird().size() >= 1) {
				mangeSelections(0, 0);
			}
		} else if(menuCollision(mx, my, 540, 100, 120, 160)) {
			if(b.getThird().size() >= 2) {
				mangeSelections(0, 1);
			}
		} else if(menuCollision(mx, my, 720, 100, 120, 160)) {
			if(b.getThird().size() >= 3) {
				mangeSelections(0, 2);
			}
		} else if(menuCollision(mx, my, 900, 100, 120, 160)) {
			if(b.getThird().size() >= 4) {
				mangeSelections(0, 3);
			}
		} else if(menuCollision(mx, my, 360, 300, 120, 160)) {
			if(b.getSecond().size() >= 1) {
				mangeSelections(1, 0);
			}
		} else if(menuCollision(mx, my, 540, 300, 120, 160)) {
			if(b.getSecond().size() >= 2) {
				mangeSelections(1, 1);
			}
		} else if(menuCollision(mx, my, 720, 300, 120, 160)) {
			if(b.getSecond().size() >= 3) {
				mangeSelections(1, 2);
			}
		} else if(menuCollision(mx, my, 900, 300, 120, 160)) {
			if(b.getSecond().size() >= 4) {
				mangeSelections(1, 3);
			}
		} else if(menuCollision(mx, my, 360, 500, 120, 160)) {
			if(b.getFirst().size() >= 1) {
				mangeSelections(2, 0);
			}
		} else if(menuCollision(mx, my, 540, 500, 120, 160)) {
			if(b.getFirst().size() >= 2) {
				mangeSelections(2, 1);
			}
		} else if(menuCollision(mx, my, 720, 500, 120, 160)) {
			if(b.getFirst().size() >= 3) {
				mangeSelections(2, 2);
			}
		} else if(menuCollision(mx, my, 900, 500, 120, 160)) {
			if(b.getFirst().size() >= 4) {
				mangeSelections(2, 3);
			}
		} else if (menuCollision(mx, my, 1200, 100, 250 ,100)) {
			//Purchase
			if (isValid()) {
				for(int i = 0; i < 3; i++) {
					for (int j = 0; j < 4; j++) {
						if (cards[i][j]) {
							purchase(i, j);
						}
					}
				}
				
				reset();
				
				game.removeMouseListener(game.buyView);
				game.addMouseListener(game.purchaseView);
				game.gameState = STATE.Purchase;
			}
			
		} else if (menuCollision(mx, my, 1200, 250, 250, 100)) {
			//Reserve
			if (isValidReserve()) {
				for(int i = 0; i < 3; i++) {
					for (int j = 0; j < 4; j++) {
						if (cards[i][j]) {
							reserve(i, j);
						}
					}
				}
				
				reset();
				
				game.removeMouseListener(game.buyView);
				game.addMouseListener(game.actionTaken);
				game.gameState = STATE.ActionTaken;
			}
			
		}  else if (menuCollision(mx, my, 1200, 400, 250, 100)) {
			//back
			reset();
			game.removeMouseListener(game.buyView);
			game.addMouseListener(game.humanTurnMenu);
			game.gameState = STATE.Game;
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Board) {
				b = (Board) o;
			} else if (o.getID() == ID.Player1) {
				h = (HumanPlayer) o;
			}
		}
		
		g.setColor(Color.CYAN);
		for (int i = 0; i < 4; i++) {
			if (cards[0][i]) g.fillRect(350 + (180 * i), 90, 140, 180);
		}
		for (int i = 0; i < 4; i++) {
			if (cards[1][i]) g.fillRect(350 + (180 * i), 290, 140, 180);
		}
		for (int i = 0; i < 4; i++) {
			if (cards[2][i]) g.fillRect(350 + (180 * i), 490, 140, 180);
		}
		
		b.renderCards(g);
		h.render(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(1200, 400, 250, 100);
		
		if(!isValid()) {
			g.setColor(Color.GRAY);
			g.fillRect(1200, 100, 250, 100);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(1200, 100, 250, 100);
		}
		
		if(!isValidReserve()) {
			g.setColor(Color.GRAY);
			g.fillRect(1200, 250, 250, 100);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(1200, 250, 250, 100);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 40));
		g.drawString("Purchase", 1250, 160);
		g.drawString("Reserve", 1250, 310);
		g.drawString("Back", 1275, 460);
		
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

	/**Determine which cards are currently selected by the player. */
	private void mangeSelections(int r, int c) {
		if(cards[r][c]) {
			numSelected--;
		} else {
			numSelected++;
		}
		cards[r][c] = !cards[r][c];
	}
	
	/** Determine whether or not the selection is a valid purchase. */
	private boolean isValid() {
		// have i selected 1 card
		if(numSelected != 1) return false;
		//have i got the money
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (cards[i][j]) {
					return hasMoney(i, j);
				}
			}
		}
		return false;
	}
	
	/**Determine whether or not the selection is a valid reserve. */
	private boolean isValidReserve() {
		return (h.getReserves().size() < 3 && numSelected == 1);
	}
	
	/** Given a card tier and card number, is the human player able to purchase the card? */
	private boolean hasMoney(int r, int c) {
		int missing = 0;
		if (r == 0) {
			cost = b.getThird().get(c).getCost();
			for(int i = 0; i < cost.length; i++) {
				if(h.getCoins()[i] + h.getCardTypes()[i] - cost[i] < 0) 
					missing -= h.getCoins()[i] + h.getCardTypes()[i] - cost[i];
			}
			if (missing > h.getCoins()[5]) return false;
		} else if (r == 1) {
			cost = b.getSecond().get(c).getCost();
			for(int i = 0; i < cost.length; i++) {
				if(h.getCoins()[i] + h.getCardTypes()[i] - cost[i] < 0) 
					missing -= h.getCoins()[i] + h.getCardTypes()[i] - cost[i];
			}
			if (missing > h.getCoins()[5]) return false;
		} else if (r == 2) {
			cost = b.getFirst().get(c).getCost();
			for(int i = 0; i < cost.length; i++) {
				if(h.getCoins()[i] + h.getCardTypes()[i] - cost[i] < 0) 
					missing -= h.getCoins()[i] + h.getCardTypes()[i] - cost[i];
			}
			if (missing > h.getCoins()[5]) return false;
		}
		return true;
	}

	/** Remove the card from the linked list and add it to the human player. */
	private void purchase(int r, int c) {
		if (r == 0) {
			h.setDebt(b.getThird().get(c).getCost().clone());
			h.setRecentPurchase(b.getThird().get(c));
			h.addCardType(b.getThird().remove(c).getColor());
		} else if (r == 1) {
			h.setDebt(b.getSecond().get(c).getCost().clone());
			h.setRecentPurchase(b.getSecond().get(c));
			h.addCardType(b.getSecond().remove(c).getColor());
		} else if (r == 2) {
			h.setDebt(b.getFirst().get(c).getCost().clone());
			h.setRecentPurchase(b.getFirst().get(c));
			h.addCardType(b.getFirst().remove(c).getColor());
			
		}
	}
	
	/** Remove the card from the linked list and add it to the human player's reserves. */
	private void reserve(int r, int c) {
		if (r == 0) {
			h.getReserves().add(b.getThird().remove(c));
		} else if (r == 1) {
			h.getReserves().add(b.getSecond().remove(c));
		} else if (r == 2) {
			h.getReserves().add(b.getFirst().remove(c));
		}
		h.addCoins(5, 1);
		b.removeCoins(5, 1);
	}

	private void reset() {
		cards = new boolean[3][4];
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++){
				cards[i][j] = false;
			}
		}
		numSelected = 0; 
	}
}
