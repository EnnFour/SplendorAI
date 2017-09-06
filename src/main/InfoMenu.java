package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;

import javax.swing.JOptionPane;

import main.Game.STATE;

public class InfoMenu extends MouseAdapter{

	private Game game;
	private Handler handler;
	
	private String line1 = "I created this AI to replicate the board game Splendor."; 
	private String line2 = "If this usage of the game is not acceptable, please email welty025@berkeley.edu.";
	private String line3 = "I will remove this project at immediate notice.";
	
	public InfoMenu(Game g, Handler h) {
		this.game = g;
		this.handler = h;
	}
	
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(menuCollision(mx, my, 1550, 40, 300, 100)) {
			game.removeMouseListener(game.infoMenu);
			game.addMouseListener(game.mainMenu);
			game.gameState = STATE.Main;
		} else if (menuCollision(mx, my, 1550, 600, 300, 100)) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://armadagames.com/splendor/"));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unable to open the rules in browser. Rules are located at https://armadagames.com/splendor/");
				}
				
			}
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 60));
		
		g.fillRect(1550, 40, 300, 100);
		g.fillRect(1550, 600, 300, 100);
		
		g.setColor(Color.BLACK);
		g.drawString("Back", 1610, 110);
		g.drawString("Rules", 1610, 670);
		g.drawString("INFO", 920, 80);
		
		g.setFont(new Font("arial", 1, 40));
		g.drawString(line1, 200, 200);
		g.drawString(line2, 200, 275);
		g.drawString(line3, 200, 350);
		
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
	
}
