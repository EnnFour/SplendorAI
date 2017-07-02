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

public class NobleVisit extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HumanPlayer h;
	private Board b;
	private boolean[] selected;
	private int numSelected;
	private int[] cost;
	private int[] have;
	
	public NobleVisit(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		selected = new boolean[]{false, false, false, false, false};
		numSelected = 0;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 800, 350, 250, 100)) {
			if(isValid()) {
				giveNoble();
				reset();
				game.removeMouseListener(game.nobleView);
				game.addMouseListener(game.aiView);
				game.gameState = STATE.AITurn;
			}
		} else if (menuCollision(mx, my, 1375, 100, 100, 100) && b.getNobles().size() >= 1) {
			handleSelection(0);
		} else if (menuCollision(mx, my, 1375, 225, 100, 100) && b.getNobles().size() >= 2) {
			handleSelection(1);
		} else if (menuCollision(mx, my, 1375, 350, 100, 100) && b.getNobles().size() >= 3) {
			handleSelection(2);
		} else if (menuCollision(mx, my, 1375, 475, 100, 100) && b.getNobles().size() >= 4) {
			handleSelection(3);
		} else if (menuCollision(mx, my, 1375, 600, 100, 100) && b.getNobles().size() >= 5) {
			handleSelection(4);
		}
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
		
		g.setColor(Color.CYAN);
		for(int i = 0; i < 5; i++) {
			if (selected[i]) g.fillRect(1365, 90 + (125 * i), 120, 120);
		}
		
		b.renderNobles(g);
		
		if(isValid()) g.setColor(Color.WHITE);
		else g.setColor(Color.GRAY);
		g.fillRect(800, 350, 250, 100);
		
		g.setColor(Color.BLACK); 
		g.setFont(new Font("arial", 1, 40));
		g.drawString("Confirm", 850, 410);
		
		g.setColor(Color.BLACK);
		g.fillRect(300, 100, 150, 200);
		
		g.setColor(Color.BLUE);
		g.fillRect(500, 100, 150, 200);
		
		g.setColor(Color.GREEN);
		g.fillRect(700, 100, 150, 200);
		
		g.setColor(Color.RED);
		g.fillRect(900, 100, 150, 200);
		
		g.setColor(Color.WHITE);
		g.fillRect(1100, 100, 150, 200);
		
		g.setColor(Color.GRAY);
		g.fillRect(320, 120, 110, 160);
		g.fillRect(520, 120, 110, 160);
		g.fillRect(720, 120, 110, 160);
		g.fillRect(920, 120, 110, 160);
		g.fillRect(1120, 120, 110, 160);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 30));
		g.drawString(h.getCardTypes()[0] + " Cards", 320, 150);
		g.drawString(h.getCardTypes()[1] + " Cards", 520, 150);
		g.drawString(h.getCardTypes()[2] + " Cards", 720, 150);
		g.drawString(h.getCardTypes()[3] + " Cards", 920, 150);
		g.drawString(h.getCardTypes()[4] + " Cards", 1120, 150);
	}
	
	private void handleSelection(int i) {
		if (selected[i]) numSelected--;
		else numSelected++;
		selected[i] = !selected[i];
	}
	
	private void reset() {
		selected = new boolean[]{false, false, false, false, false};
		numSelected = 0;
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
		for (int i = 0; i < 5; i++) {
			if (selected[i]) cost = b.getNobles().get(i).getCost();
		}
		have = h.getCardTypes();
		int[] temp = new int[5];
		for (int i = 0; i < 5; i++) {
			temp[i] = have[i] - cost[i];
		}
		return (temp[0] >= 0 && temp[1] >= 0 && temp[2] >= 0 && temp[3] >= 0 && temp[4] >= 0);
	}

	private void giveNoble() {
		for (int i = 0; i < 5; i++) {
			if (selected[i]) {
				b.removeNoble(i);
				h.setNobles(h.getNobles() + 1);
				h.setVictoryPoints(h.getVictoryPoints() + 3);
			}
		}
	}
	
}
