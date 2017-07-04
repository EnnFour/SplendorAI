package actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game;
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
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 1, 90));
		g.drawString("Game Over!", 100, 100);
		
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Player1) {
				g.drawString("You had " + Integer.toString(((Player) o).getVictoryPoints()) + " Victory Points", 200, 200);
			} else if (o.getID() == ID.Player2) {
				
			} else if (o.getID() == ID.Player3) {
				
			} else if (o.getID() == ID.Player4) {
				
			}
		}
		
		
		
	}
	
	private void reset() {
		
	}
	

}
