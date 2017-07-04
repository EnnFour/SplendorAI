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

public class Purchase extends MouseAdapter{

	private Game game;
	private Handler handler;
	private HumanPlayer h;
	private int[] coins;
	private int[] offer;
	private int[] debt;
	boolean bonus;
	private Board b;
	private int[] cards;
	
	
	public Purchase(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		offer = new int[]{0,0,0,0,0,0};
		bonus = false;
		cards = new int[]{0,0,0,0,0};
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (menuCollision(mx, my, 1500, 100, 250, 100)) {
			if(isValid()){
				reset();
				h.setVictoryPoints(h.getVictoryPoints() + h.getRecentPurchase().getVictoryPoints());
				game.removeMouseListener(game.purchaseView);
				game.addMouseListener(game.actionTaken);
				if (game.getMouseListeners().length != 1) {
					throw new IllegalStateException("More than one MouseListener");
				}
				game.gameState = STATE.ActionTaken;	
			}
		} else if (menuCollision(mx, my, 150, 525, 50, 50)) {
			addToOffer(0, 1);
		} else if (menuCollision(mx, my, 350, 525, 50, 50)) {
			addToOffer(1, 1);
		} else if (menuCollision(mx, my, 550, 525, 50, 50)) {
			addToOffer(2, 1);
		} else if (menuCollision(mx, my, 750, 525, 50, 50)) {
			addToOffer(3, 1);
		} else if (menuCollision(mx, my, 950, 525, 50, 50)) {
			addToOffer(4, 1);
		} else if (menuCollision(mx, my, 1150, 525, 50, 50)) {
			addToOffer(5, 1);
		} else if (menuCollision(mx, my, 150, 775, 50, 50)) {
			addToOffer(0, -1);
		} else if (menuCollision(mx, my, 350, 775, 50, 50)) {
			addToOffer(1, -1);
		} else if (menuCollision(mx, my, 550, 775, 50, 50)) {
			addToOffer(2, -1);
		} else if (menuCollision(mx, my, 750, 775, 50, 50)) {
			addToOffer(3, -1);
		} else if (menuCollision(mx, my, 950, 775, 50, 50)) {
			addToOffer(4, -1);
		} else if (menuCollision(mx, my, 1150, 775, 50, 50)) {
			addToOffer(5, -1);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) h = (HumanPlayer) o;
			else if (o.getID() == ID.Board) b = (Board) o;
		}
		coins = h.getCoins();
		debt = h.getDebt();
		if(!bonus) {
			for (int i = 0; i < 5; i++) {
				debt[i] -= h.getCardTypes()[i];
			}
			bonus = !bonus;
			debt[h.getRecentPurchase().getColor()] += 1;
			
			for(int i = 0; i < 5; i++) {
				if (debt[i] < 0) debt[i] = 0;
			}
			
			cards = h.getCardTypes().clone();
			cards[h.getRecentPurchase().getColor()] -= 1;
			
		}
		
		
		h.getRecentPurchase().render(100, 100, g);
		
		g.setColor(Color.BLACK);
		g.fillOval(100, 300, 150, 150);
		g.fillOval(100, 600, 150, 150);
		g.fillRect(1350, 400, 120, 160);
		
		g.setColor(Color.BLUE);
		g.fillOval(300, 300, 150, 150);
		g.fillOval(300, 600, 150, 150);
		g.fillRect(1490, 400, 120, 160);
		
		g.setColor(Color.GREEN);
		g.fillOval(500, 300, 150, 150);
		g.fillOval(500, 600, 150, 150);
		g.fillRect(1630, 400, 120, 160);
		
		g.setColor(Color.RED);
		g.fillOval(700, 300, 150, 150);
		g.fillOval(700, 600, 150, 150);
		g.fillRect(1350, 600, 120, 160);
		
		g.setColor(Color.WHITE);
		g.fillOval(900, 300, 150, 150);
		g.fillOval(900, 600, 150, 150);
		g.fillRect(1490, 600, 120, 160);
		
		g.setColor(Color.ORANGE);
		g.fillOval(1100, 300, 150, 150);
		g.fillOval(1100, 600, 150, 150);
		
		g.setColor(Color.GRAY);
		g.fillOval(120, 320, 110, 110);
		g.fillOval(120, 620, 110, 110);
		g.fillOval(320, 320, 110, 110);
		g.fillOval(320, 620, 110, 110);
		g.fillOval(520, 320, 110, 110);
		g.fillOval(520, 620, 110, 110);
		g.fillOval(720, 320, 110, 110);
		g.fillOval(720, 620, 110, 110);
		g.fillOval(920, 320, 110, 110);
		g.fillOval(920, 620, 110, 110);
		g.fillOval(1120, 320, 110, 110);
		g.fillOval(1120, 620, 110, 110);
		
		g.fillRect(1370, 420, 80, 120);
		g.fillRect(1510, 420, 80, 120);
		g.fillRect(1650, 420, 80, 120);
		g.fillRect(1370, 620, 80, 120);
		g.fillRect(1510, 620, 80, 120);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 40));
		
		g.drawString(Integer.toString(offer[0]), 165, 385);
		g.drawString(Integer.toString(offer[1]), 365, 385);
		g.drawString(Integer.toString(offer[2]), 565, 385);
		g.drawString(Integer.toString(offer[3]), 765, 385);
		g.drawString(Integer.toString(offer[4]), 965, 385);
		g.drawString(Integer.toString(offer[5]), 1165, 385);
		
		g.drawString(Integer.toString(coins[0]), 165, 685);
		g.drawString(Integer.toString(coins[1]), 365, 685);
		g.drawString(Integer.toString(coins[2]), 565, 685);
		g.drawString(Integer.toString(coins[3]), 765, 685);
		g.drawString(Integer.toString(coins[4]), 965, 685);
		g.drawString(Integer.toString(coins[5]), 1165, 685);
		
		g.setFont(new Font("arial", 1, 20));
		g.drawString(cards[0] + " Cards", 1370, 440);
		g.drawString(cards[1] + " Cards", 1510, 440);
		g.drawString(cards[2] + " Cards", 1650, 440);
		g.drawString(cards[3] + " Cards", 1370, 640);
		g.drawString(cards[4] + " Cards", 1510, 640);
		
		g.setFont(new Font("arial", 1, 40));
		
		g.fillRect(150, 525, 50, 50);
		g.fillRect(350, 525, 50, 50);
		g.fillRect(550, 525, 50, 50);
		g.fillRect(750, 525, 50, 50);
		g.fillRect(950, 525, 50, 50);
		g.fillRect(1150, 525, 50, 50);
		
		g.fillRect(150, 775, 50, 50);
		g.fillRect(350, 775, 50, 50);
		g.fillRect(550, 775, 50, 50);
		g.fillRect(750, 775, 50, 50);
		g.fillRect(950, 775, 50, 50);
		g.fillRect(1150, 775, 50, 50);
		
		if (!isValid()){
			g.setColor(Color.GRAY);
		}
		g.fillRect(1500, 100, 250, 100);
		
		g.setColor(Color.BLACK);
		g.drawString("+", 165, 565);
		g.drawString("+", 365, 565);
		g.drawString("+", 565, 565);
		g.drawString("+", 765, 565);
		g.drawString("+", 965, 565);
		g.drawString("+", 1165, 565);
		
		g.drawString("-", 165, 810);
		g.drawString("-", 365, 810);
		g.drawString("-", 565, 810);
		g.drawString("-", 765, 810);
		g.drawString("-", 965, 810);
		g.drawString("-", 1165, 810);
		
		g.drawString("Confirm", 1550, 160);
	}
	
	private void addToOffer(int coin, int amt) {
		if(coin == 5){
			if(offer[coin] + amt >= 0 && coins[coin] - amt >= 0) {
				offer[coin] += amt;
				h.addCoins(coin, -1 * amt);
				b.removeCoins(coin, -1 * amt);
			}
			
		} else if (offer[coin] + amt >= 0 && coins[coin] - amt >= 0 && offer[coin] + amt <= debt[coin]) {
			offer[coin] += amt;
			h.addCoins(coin, -1 * amt);
			b.removeCoins(coin, -1 * amt);
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
	
	private boolean isValid() {
		int x = 0;
		int y = 0;
		for (int i : offer) {
			x += i;
		}
		for(int j : debt) {
			y += j;
		}
		return (x == y);
	}
	
	private void reset() {
		h.setDebt(new int[]{0,0,0,0,0});
		offer = new int[]{0,0,0,0,0,0};
		bonus = false;
	}
}
