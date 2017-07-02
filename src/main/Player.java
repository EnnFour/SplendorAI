package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Player extends GameObject{

	protected int[] coins = new int[6]; //Black Blue Green Red White Gold
	protected int victoryPoints;
	protected int[] numCardTypes = new int[5]; //Black Blue Green Red White
	protected int nobles;
	protected LinkedList<Card> reserves;
	protected int[] debt = new int[5];
	protected Card recentPurchase;
	
	public Player(ID id, Game game) {
		super(id, game);
		
		for (int i = 0; i < coins.length; i++) {
			coins[i] = 0;
		}
		for (int i = 0; i < numCardTypes.length; i++) {
			numCardTypes[i] = 0;
			debt[i] = 0;
		}
		victoryPoints = 0;
		nobles = 0;
		reserves = new LinkedList<>();
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getNobles() {
		return nobles;
	}

	public void setNobles(int nobles) {
		this.nobles = nobles;
	}

	public void addCardType(int type){
		numCardTypes[type] += 1;
	}
	
	public int[] getCardTypes() {
		return numCardTypes;
	}
	
	public void addCoins(int type, int amt) {
		coins[type] += amt;
	}
	
	public int[] getCoins() {
		return coins;
	}

	public LinkedList<Card> getReserves() {
		return reserves;
	}
	
	public int[] getDebt() {
		return debt;
	}

	public void setDebt(int [] debt){
		this.debt = debt;
	}
	
	public int getNumCoins() {
		int x = 0;
		for (int i : coins) {
			x += i;
		}
		return x;
	}
	
	public void setRecentPurchase(Card c) {
		recentPurchase = c;
	}
	
	public Card getRecentPurchase() {
		return recentPurchase;
	}
}
