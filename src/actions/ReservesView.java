package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game;
import main.GameObject;
import main.Handler;
import main.HumanPlayer;
import main.ID;
import main.Game.STATE;

public class ReservesView extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HumanPlayer h;
	private boolean[] selected;
	private int numSelected;
	private int numReserved;
	private int cost[];
	private int missing;
	
	public ReservesView(Game g, Handler handler) {
		game = g;
		this.handler = handler;
		selected = new boolean[]{false, false, false};
		numSelected = 0;
		numReserved = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (menuCollision(mx, my, 200, 420, 250, 100)) {
			
			if (isValid()) {
				for(int i = 0; i < 3; i++) {
						if (selected[i]) {
							purchase(i);
						}
				}
				
				reset();
				
				game.removeMouseListener(game.buyView);
				game.addMouseListener(game.purchaseView);
				game.gameState = STATE.Purchase;
			}
		} else if (menuCollision(mx, my, 200, 570, 250, 100)) {
			reset();
			game.removeMouseListener(game.reservesView);
			game.addMouseListener(game.humanTurnMenu);
			game.gameState = STATE.Game;
		} else if (menuCollision(mx, my, 200, 200, 120, 160)) {
			if (numReserved >= 1) {
				if(selected[0]) numSelected--;
				else numSelected++;
				selected[0] = !selected[0];
			}
		} else if (menuCollision(mx, my, 380, 200, 120, 160)) {
			if (numReserved >= 2) {
				if(selected[1]) numSelected--;
				else numSelected++;
				selected[1] = !selected[1];
			}
		} else if (menuCollision(mx, my, 560, 200, 120, 160)) {
			
			if(numReserved >= 3) {
				if(selected[2]) numSelected--;
				else numSelected++;
				selected[2] = !selected[2];
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		for(GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) h = (HumanPlayer) o;
		}
		
		numReserved = h.getReserves().size();
		
		g.setColor(Color.CYAN);
		for(int i = 0; i < 3; i++) {
			if (selected[i]) g.fillRect(190 + (180 * i), 190, 140, 180);
		}
		
		for (int i = 0; i < numReserved; i++) {
			h.getReserves().get(i).render(200 + (180 * i), 200, g);
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(200, 570, 250, 100);
		
		if (!isValid()) {
			g.setColor(Color.GRAY);
		}
		g.fillRect(200, 420, 250, 100);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 60));
		g.drawString("Buy", 270, 490);
		g.drawString("Back", 250, 640);
		g.drawString("Reserved Cards:", 200, 150);

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
	
	private boolean isValid() {
		if (numSelected != 1) return false;
		for (int i = 0; i < 3; i++) {
			if (selected[i]) {
				return hasMoney(i);
			}
		}
		return false;
	}
	
	private void reset() {
		selected = new boolean[]{false, false, false};
		numSelected = 0;
		
	}
	
	private boolean hasMoney (int x) {
		cost = h.getReserves().get(x).getCost();
		missing = 0;
		
		for(int i = 0; i < cost.length; i++) {
			if(h.getCoins()[i] + h.getCardTypes()[i] - cost[i] < 0) 
				missing -= h.getCoins()[i] + h.getCardTypes()[i] - cost[i];
		}
		if (missing > h.getCoins()[5]) return false;
		return true;
	}
	
	private void purchase(int i) {
		h.setDebt(h.getReserves().get(i).getCost().clone());
		h.setRecentPurchase(h.getReserves().get(i));
		h.addCardType(h.getReserves().remove(i).getColor());
	}	
}
