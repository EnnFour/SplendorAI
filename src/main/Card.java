package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

public class Card extends Buyable {
	
	private int color;
	private int tier;
	
	public Card(int black, int blue, int green, int red, int white, int c, int v, int t) {
		super(black, blue, green, red, white, v);
		color = c; // bk = 0, bl = 1, g = 2, r = 3, w = 4
		tier = t;
	}

	public int getColor() {
		return color;
	}
	
	public int getTier() {
		return tier;
	}
	
	public void render(int x, int y, Graphics g) {
		if (tier == 1) {
			g.setColor(Color.GREEN);
		} else if (tier == 2) {
			g.setColor(new Color(175,175,0));
		} else if (tier == 3) {
			g.setColor(new Color(0,0,175));
		}
		
		g.fillRect(x, y, 120, 160);
		g.setFont(new Font("arial", 1, 15));
		g.setColor(Color.WHITE);
		
		//xy = 100 100
		g.drawString("VPs", x + 10, y + 20);
		g.drawString(Integer.toString(victoryPoints), x + 80, y + 20);
		
		g.drawString("gem", x + 10, y + 40);
		
		g.drawString("black", x + 10, y + 60);
		g.drawString(Integer.toString(cost[0]), x + 80, y + 60);
		
		g.drawString("blue", x + 10, y + 80);
		g.drawString(Integer.toString(cost[1]), x + 80, y + 80);
		
		g.drawString("green", x + 10, y + 100);
		g.drawString(Integer.toString(cost[2]), x + 80, y + 100);
		
		g.drawString("red", x + 10, y + 120);
		g.drawString(Integer.toString(cost[3]), x + 80, y + 120);
		
		g.drawString("white", x + 10, y + 140);
		g.drawString(Integer.toString(cost[4]), x + 80, y + 140);
		
		if(color == 0) {
			g.setColor(Color.BLACK);
		} else if (color == 1) {
			g.setColor(new Color(75, 75, 255));
		} else if (color == 2) {
			g.setColor(Color.GREEN);
		} else if (color == 3) {
			g.setColor(Color.RED);
		} else if (color == 4) {
			g.setColor(Color.WHITE);
		}
		
		g.fillRect(x + 80, y + 25, 30, 15);
		g.setColor(Color.BLACK);
		g.drawRect(x + 80, y + 25, 30, 15);
	}

	public void print() {
		System.out.print("Tier: " + tier);
		System.out.print("   VPs: " + victoryPoints);
		System.out.print("   Cost: ");
		for (int i = 0; i < 5; i++) {
			System.out.print(cost[i] + " ");
		}
		System.out.println();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) return false;
		
		Card other = (Card) o;
		
		boolean ans = true;
		
		ans = ans && this.getColor() == other.getColor();
		ans = ans && this.getTier() == other.getTier();
		ans = ans && Arrays.equals(this.getCost(), other.getCost());
		
		return ans;
	}
}
