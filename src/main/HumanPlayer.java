package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HumanPlayer extends Player {

	public HumanPlayer(ID id, Game game) {
		super(id, game);
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(1150, 800, 150, 150);
		
		g.setColor(Color.BLACK);
		g.fillRect(100, 800, 120, 160);
		g.fillOval(850, 800, 70, 70);
		
		
		g.setColor(Color.BLUE);
		g.fillRect(250, 800, 120, 160);
		g.fillOval(950, 800, 70, 70);
		
		g.setColor(Color.GREEN);
		g.fillRect(400, 800, 120, 160);
		g.fillOval(1050, 800, 70, 70);
		
		g.setColor(Color.RED);
		g.fillRect(550, 800, 120, 160);
		g.fillOval(850, 880, 70, 70);
		
		g.setColor(Color.WHITE);
		g.fillRect(700, 800, 120, 160);
		g.fillOval(950, 880, 70, 70);
		
		g.setColor(Color.ORANGE);
		g.fillOval(1050, 880, 70, 70);
		
		g.setColor(Color.GRAY);
		g.fillOval(860, 810, 50, 50);
		g.fillOval(960, 810, 50, 50);
		g.fillOval(1060, 810, 50, 50);
		g.fillOval(860, 890, 50, 50);
		g.fillOval(960, 890, 50, 50);
		g.fillOval(1060, 890, 50, 50);
		g.fillRect(120, 820, 80, 120);
		g.fillRect(270, 820, 80, 120);
		g.fillRect(420, 820, 80, 120);
		g.fillRect(570, 820, 80, 120);
		g.fillRect(720, 820, 80, 120);
		g.fillRect(1170, 820, 110, 110);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 20));
		g.drawString(numCardTypes[0] + " Cards", 125, 840);
		g.drawString(numCardTypes[1] + " Cards", 275, 840);
		g.drawString(numCardTypes[2] + " Cards", 425, 840);
		g.drawString(numCardTypes[3] + " Cards", 575, 840);
		g.drawString(numCardTypes[4] + " Cards", 725, 840);
		
		g.drawString(Integer.toString(coins[0]), 880, 840);
		g.drawString(Integer.toString(coins[1]), 980, 840);
		g.drawString(Integer.toString(coins[2]), 1080, 840);
		g.drawString(Integer.toString(coins[3]), 880, 920);
		g.drawString(Integer.toString(coins[4]), 980, 920);
		g.drawString(Integer.toString(coins[5]), 1080, 920);
		
		g.drawString(nobles + " Nobles", 1175, 840);
		g.drawString(victoryPoints + " VPs", 1175, 860);
		g.drawString(getReserves().size() + " Reserved", 1175, 880);
	}

}
