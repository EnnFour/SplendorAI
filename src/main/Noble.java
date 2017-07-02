package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Noble extends Buyable {

	public Noble(int black, int blue, int green, int red, int white) {
		super(black, blue, green, red, white, 3);
	}
	
	public void render(int x, int y, Graphics g) {
		
		g.setColor(new Color(175, 175, 0));
		g.fillRect(x, y + 50, 100, 50);
		g.setFont(new Font("arial", 1, 20));
		
		if(numColors() == 2) {
			int offset = 0;
		
			if (cost[0] != 0) {
				g.setColor(Color.BLACK);
				g.fillRect(x + offset, y, 50, 50);
				g.setColor(Color.WHITE);
				g.drawString("4", x + 20 + offset, y + 85);
				offset += 50;
			}
			if (cost[1] != 0) {
				g.setColor(Color.BLUE);
				g.fillRect(x + offset, y, 50, 50);
				g.setColor(Color.WHITE);
				g.drawString("4", x + 20 + offset, y + 85);
				offset += 50;	
			}
			if (cost[2] != 0) {
				g.setColor(Color.GREEN);
				g.fillRect(x + offset, y, 50, 50);
				g.setColor(Color.WHITE);
				g.drawString("4", x + 20 + offset, y + 85);
				offset += 50;
			}
			if (cost[3] != 0) {
				g.setColor(Color.RED);
				g.fillRect(x + offset, y, 50, 50);
				g.setColor(Color.WHITE);
				g.drawString("4", x + 20 + offset, y + 85);
				offset += 50;
			}
			if (cost[4] != 0) {
				g.setColor(Color.WHITE);
				g.fillRect(x + offset, y, 50, 50);
				g.setColor(Color.WHITE);
				g.drawString("4", x + 20 + offset, y + 85);
				offset += 50;
			}
			
		} else {
			int offset = 0;
			
			if (cost[0] != 0) {
				g.setColor(Color.BLACK);
				g.fillRect(x + offset, y, 33, 50);
				g.setColor(Color.WHITE);
				g.drawString("3", x + 10 + offset, y + 85);
				offset += 33;
			}
			if (cost[1] != 0) {
				g.setColor(Color.BLUE);
				g.fillRect(x + offset, y, 33, 50);
				g.setColor(Color.WHITE);
				g.drawString("3", x + 10 + offset, y + 85);
				offset += 33;	
			}
			if (cost[2] != 0) {
				g.setColor(Color.GREEN);
				g.fillRect(x + offset, y, 33, 50);
				g.setColor(Color.WHITE);
				g.drawString("3", x + 10 + offset, y + 85);
				offset += 33;
			}
			if (cost[3] != 0) {
				g.setColor(Color.RED);
				g.fillRect(x + offset, y, 33, 50);
				g.setColor(Color.WHITE);
				g.drawString("3", x + 10 + offset, y + 85);
				offset += 33;
			}
			if (cost[4] != 0) {
				g.setColor(Color.WHITE);
				g.fillRect(x + offset, y, 33, 50);
				g.setColor(Color.WHITE);
				g.drawString("3", x + 10 + offset, y + 85);
				offset += 33;
			}
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 100, 100);
	}
	
	private int numColors() {
		int i = 0;
		for (int x : cost) {
			if (x != 0) i++;
		}
		return i;
	}
}
