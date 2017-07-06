package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Board extends GameObject{

	private LinkedList<Card> first;
	private LinkedList<Card> second;
	private LinkedList<Card> third;
	private LinkedList<Noble> nobles;
	private LinkedList<Player> players = new LinkedList<>();
	private int[] coins;
	private int availableNobles;
	private int numPlayers;
	
	/** Create board given players. */
	public Board(ID id, Game game, int players) {
		super(id, game);
		
		numPlayers = players;
		availableNobles = players + 1;
		
		Card[] f = makeFirstTier();
		Card[] s = makeSecondTier();
		Card[] t = makeThirdTier();
		Noble[] n = makeNobles(players);
		
		first = new LinkedList<>();
		second = new LinkedList<>();
		third = new LinkedList<>();
		nobles = new LinkedList<>();
		
		for(Card c : f) {
			first.add(c);
		}
		
		for(Card c : s) {
			second.add(c);
		}
		for (Card c : t) {
			third.add(c);
		}
		for(Noble x : n) {
			nobles.add(x);
		}
		
		coins = new int[6];
		
		if (players == 2) {
			for(int i = 0; i < coins.length - 1; i++) {
				coins[i] = 4;
			}
			coins[5] = 5;
		} else if (players == 3) {
			for(int i = 0; i < coins.length - 1; i++) {
				coins[i] = 5;
			}
			coins[5] = 5;
		} else {
			for(int i = 0; i < coins.length - 1; i++) {
				coins[i] = 7;
			}
			coins[5] = 5;
		}	
	}
	
	/** Create an array with all 40 tier 1 cards. */
	private Card[] makeFirstTier() {
		Card[] arr = new Card[40];
		//Card constructor: Bk, bl, gr, rd, wh, color, vp, tier
		arr[0] = new Card(0,1,1,1,1,0,0,1);
		arr[1] = new Card(0,2,1,1,1,0,0,1);
		arr[2] = new Card(0,2,0,1,2,0,0,1);
		arr[3] = new Card(1,0,1,3,0,0,0,1);
		
		arr[4] = new Card(0,0,2,1,0,0,0,1);
		arr[5] = new Card(0,0,2,0,2,0,0,1);
		arr[6] = new Card(0,0,3,0,0,0,0,1);
		arr[7] = new Card(0,4,0,0,0,0,1,1);
		
		arr[8] = new Card(1,0,1,1,1,1,0,1);
		arr[9] = new Card(1,0,1,2,1,1,0,1);
		arr[10] = new Card(0,0,2,2,1,1,0,1);
		arr[11] = new Card(0,1,3,1,0,1,0,1);
		
		arr[12] = new Card(2,0,0,0,1,1,0,1);
		arr[13] = new Card(2,0,2,0,0,1,0,1);
		arr[14] = new Card(3,0,0,0,0,1,0,1);
		arr[15] = new Card(0,0,0,4,0,1,1,1);
	
		arr[16] = new Card(1,1,1,1,0,4,0,1);
		arr[17] = new Card(1,1,2,1,0,4,0,1);
		arr[18] = new Card(1,2,2,0,0,4,0,1);
		arr[19] = new Card(1,1,0,0,3,4,0,1);
		
		arr[20] = new Card(1,0,0,2,0,4,0,1);
		arr[21] = new Card(2,2,0,0,0,4,0,1);
		arr[22] = new Card(0,3,0,0,0,4,0,1);
		arr[23] = new Card(0,0,4,0,0,4,1,1);
		
		arr[24] = new Card(1,1,0,1,1,2,0,1);
		arr[25] = new Card(2,1,0,1,1,2,0,1);
		arr[26] = new Card(2,1,0,2,0,2,0,1);
		arr[27] = new Card(0,3,1,0,1,2,0,1);
		
		arr[28] = new Card(0,1,0,0,2,2,0,1);
		arr[29] = new Card(0,2,0,2,0,2,0,1);
		arr[30] = new Card(0,0,0,3,0,2,0,1);
		arr[31] = new Card(4,0,0,0,0,2,1,0);
		
		arr[32] = new Card(1,1,1,0,1,3,0,1);
		arr[33] = new Card(1,1,1,0,2,3,0,1);
		arr[34] = new Card(2,0,1,0,2,3,0,1);
		arr[35] = new Card(3,0,0,1,1,3,0,1);
		
		arr[36] = new Card(0,2,1,0,0,3,0,1);
		arr[37] = new Card(0,0,0,2,2,3,0,1);
		arr[38] = new Card(0,0,0,0,3,3,0,1);
		arr[39] = new Card(0,0,0,0,4,3,1,1);
		
		shuffle(arr);
		return arr;
	}
	
	/** Create an array with all 30 tier 2 cards. */
	private Card[] makeSecondTier() {
		Card[] arr = new Card[30];
		
		arr[0] = new Card(0,2,2,0,3,0,1,2);
		arr[1] = new Card(2,0,3,0,3,0,1,2);
		arr[2] = new Card(0,1,4,2,0,0,2,2);
		arr[3] = new Card(0,0,5,3,0,0,2,2);
		arr[4] = new Card(0,0,0,0,5,0,2,2);
		arr[5] = new Card(6,0,0,0,0,0,3,2);
		
		arr[6] = new Card(0,2,2,3,0,1,1,2);
		arr[7] = new Card(3,2,3,0,0,1,1,2);
		arr[8] = new Card(0,3,0,0,5,1,2,2);
		arr[9] = new Card(4,0,0,1,2,1,2,2);
		arr[10] = new Card(0,5,0,0,0,1,2,2);
		arr[11] = new Card(0,6,0,0,0,1,3,2);
		
		arr[12] = new Card(2,0,3,2,0,4,1,2);
		arr[13] = new Card(0,3,0,3,2,4,1,2);
		arr[14] = new Card(2,0,1,4,0,4,2,2);
		arr[15] = new Card(3,0,0,5,0,4,2,2);
		arr[16] = new Card(0,0,0,5,0,4,2,2);
		arr[17] = new Card(0,0,0,0,6,4,3,2);
		
		arr[18] = new Card(0,0,2,3,3,2,1,2);
		arr[19] = new Card(2,3,0,0,2,2,1,2);
		arr[20] = new Card(1,2,0,0,4,2,2,2);
		arr[21] = new Card(0,5,3,0,0,2,2,2);
		arr[22] = new Card(0,0,5,0,0,2,2,2);
		arr[23] = new Card(0,0,6,0,0,2,3,2);
		
		arr[24] = new Card(3,0,0,2,2,3,1,2);
		arr[25] = new Card(3,3,0,2,0,3,1,2);
		arr[26] = new Card(0,4,2,0,1,3,2,2);
		arr[27] = new Card(5,0,0,0,3,3,2,2);
		arr[28] = new Card(5,0,0,0,0,3,2,2);
		arr[29] = new Card(0,0,0,6,0,3,3,2);
		
		shuffle(arr);
		return arr;
		
	}
	
	/** Create an array with all 20 tier 2 cards. */
	private Card[] makeThirdTier() {
		Card[] arr = new Card[20];
		
		arr[0] = new Card(0,3,5,3,3,0,3,3);
		arr[1] = new Card(0,0,0,7,0,0,4,3);
		arr[2] = new Card(3,0,3,6,0,0,4,3);
		arr[3] = new Card(3,0,0,7,0,0,5,3);
		
		arr[4] = new Card(5,0,3,3,3,1,3,3);
		arr[5] = new Card(0,0,0,0,7,1,4,3);
		arr[6] = new Card(3,3,0,0,6,1,4,3);
		arr[7] = new Card(0,3,0,0,7,1,5,3);
		
		arr[8] = new Card(3,3,3,5,0,4,3,3);
		arr[9] = new Card(7,0,0,0,0,4,4,3);
		arr[10] = new Card(6,0,0,3,3,4,4,3);
		arr[11] = new Card(7,0,0,0,3,4,5,3);
		
		arr[12] = new Card(3,3,0,3,5,2,3,3);
		arr[13] = new Card(0,7,0,0,0,2,4,3);
		arr[14] = new Card(0,6,3,0,3,2,4,3);
		arr[15] = new Card(0,7,3,0,0,2,5,3);
	
		arr[16] = new Card(3,5,3,0,3,3,3,3);
		arr[17] = new Card(0,0,7,0,0,3,4,3);
		arr[18] = new Card(0,3,6,3,0,3,4,3);
		arr[19] = new Card(0,0,7,3,0,3,5,3);
		
		shuffle(arr);
		return arr;
	}
	
	/** Shuffles an array of card objects. */
	private void shuffle(Card[] deck) {
		int len = deck.length;
		Random rand = new Random();
		int n = 0;
		for (int i = len-1; i > 0; i--) {
			n = rand.nextInt(i+1);
			Card temp = deck[i];
			deck[i] = deck[n];
			deck[n] = temp;
		}
	}
	
	/** Shuffles an array of Noble objects. */
	private void shuffle(Noble[] deck) {
		int len = deck.length;
		Random rand = new Random();
		int n = 0;
		for (int i = len-1; i > 0; i--) {
			n = rand.nextInt(i+1);
			Noble temp = deck[i];
			deck[i] = deck[n];
			deck[n] = temp;
		}
	}
	
	/** Make an array of all the nobles, and then return an array with the correct number */
	private Noble[] makeNobles(int players) {
		//Noble Constructor: bk, bl, gr, r, wh
		Noble[] arr = new Noble[10];
		
		arr[0] = new Noble(0,0,4,4,0);
		arr[1] = new Noble(3,0,0,3,3);
		arr[2] = new Noble(0,4,0,0,4);
		arr[3] = new Noble(4,0,0,0,4);
		arr[4] = new Noble(0,4,4,0,0);
		
		arr[5] = new Noble(0,3,3,3,0);
		arr[6] = new Noble(0,3,3,0,3);
		arr[7] = new Noble(4,0,0,4,0);
		arr[8] = new Noble(3,3,0,0,3);
		arr[9] = new Noble(3,0,3,3,0);
		
		shuffle(arr);
		Noble[] temp = new Noble[players+1];
		for (int i = 0; i < players+1; i++) {
			temp[i] = arr[i];
		}
		return temp;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		
		renderCards(g);
		renderCoins(g);
		renderNobles(g);
		
	}

	public void renderCards(Graphics g) {
		for(int i = 0; i < 4 && i < first.size(); i++) {
			first.get(i).render(360 + (180 * i), 500, g);
		}
		
		for(int i = 0; i < 4 && i < second.size(); i++) {
			second.get(i).render(360 + (180 * i), 300, g);
		}
		
		for(int i = 0; i < 4 && i < third.size(); i++) {
			third.get(i).render(360 + (180 * i), 100, g);
		}
		
		g.setColor(new Color(0,0,175));
		g.fillRect(100, 100, 120, 160);
		g.setColor(new Color(175, 175, 0));
		g.fillRect(100, 300, 120, 160);
		g.setColor(Color.GREEN);
		g.fillRect(100, 500, 120, 160);
		
		g.setFont(new Font("arial", 1, 40));
		g.setColor(Color.WHITE);
		int firstRemain = Math.max(first.size() - 4, 0);
		int secondRemain = Math.max(second.size() - 4, 0);
		int thirdRemain = Math.max(third.size() - 4, 0);
		
		g.drawString(Integer.toString(thirdRemain), 140, 180);
		g.drawString(Integer.toString(secondRemain), 140, 380);
		g.drawString(Integer.toString(firstRemain), 140, 580);
	}
	
	private void renderCoins(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(1075, 100, 100, 100);
		g.setColor(Color.BLACK);
		g.fillOval(1200, 100, 100, 100);
		g.setColor(Color.GREEN);
		g.fillOval(1075, 250, 100, 100);
		g.setColor(Color.RED);
		g.fillOval(1200, 250, 100, 100);
		g.setColor(Color.WHITE);
		g.fillOval(1075, 400, 100, 100);
		g.setColor(Color.ORANGE);
		g.fillOval(1200, 400, 100, 100);
		
		g.setColor(Color.GRAY);
		g.fillOval(1088, 113, 75, 75);
		g.fillOval(1213, 113, 75, 75);
		g.fillOval(1088, 263, 75, 75);
		g.fillOval(1213, 263, 75, 75);
		g.fillOval(1088, 413, 75, 75);
		g.fillOval(1213, 413, 75, 75);
		
		g.setFont(new Font("arial", 1, 30));
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(coins[1]), 1118, 158);//blu
		g.drawString(Integer.toString(coins[0]), 1243, 158);//black
		g.drawString(Integer.toString(coins[2]), 1118, 305);//grn
		g.drawString(Integer.toString(coins[3]), 1243, 305);//red
		g.drawString(Integer.toString(coins[4]), 1118, 458);//white
		g.drawString(Integer.toString(coins[5]), 1243, 458);//gold
	}
	
	public void renderNobles(Graphics g) {
		for(int i = 0; i < availableNobles; i++) {
			nobles.get(i).render(1375, 100 + (i * 125), g);
		}
	}
	
	public Card purchaseFirstTier(int i) {
		return first.remove(i);
	}
	
	public Card purchaseSecondTier(int i) {
		return second.remove(i);
	}
	
	public Card purchaseThirdTier(int i) {
		return third.remove(i);
	}
	
	public Noble removeNoble(int i) {
		availableNobles--;
		return nobles.remove(i);
	}
	
	public int[] getCoins() {
		return coins;
	}
	
	public void removeCoins(int type, int amt) {
		coins[type] = coins[type] - amt;
	}
	
	public LinkedList<Card> getThird() {
		return third;
	}
	
	public LinkedList<Card> getSecond() {
		return second;
	}
	
	public LinkedList<Card> getFirst() {
		return first;
	}

	public LinkedList<Noble> getNobles() {
		return nobles;
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void remove(Card c) {
		for (int i = 0; i < first.size(); i++) {
			if (c.equals(first.get(i))) first.remove(i);
		}
		
		for (int i = 0; i < second.size(); i++) {
			if (c.equals(second.get(i))) second.remove(i);
		}
		
		for (int i = 0; i < third.size(); i++) {
			if (c.equals(third.get(i))) third.remove(i);
		}
	}
}
