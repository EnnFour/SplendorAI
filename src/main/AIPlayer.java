package main;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.Test;

import main.Game.STATE;
import utilities.MapNode;

public class AIPlayer extends Player{

	private Handler handler;
	private Board b;
	private PriorityQueue<MapNode<Double, Card>> pq;
	private Card[] topChoices;
	private LinkedList<Card> availableCards;
	private LinkedList<Noble> availableNobles;
	private LinkedList<Player> otherPlayers = new LinkedList<>();
	
	public AIPlayer(ID id, Game game, Handler handler) {
		super(id, game);
		this.handler = handler;
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}

	public void takeTurn() {
		
		if (game.getEndReached() && game.getTurnsRemaining() == 0) {
			return;
		} else if (game.getEndReached()) {
			game.setTurnsRemaining(game.getTurnsRemaining() - 1);
		}
		
		
		for (GameObject o : handler.getObjects()) {
			if (o.getID() == ID.Board) b = (Board) o;
			if (o.getID() != ID.Board && o.getID() != this.getID()) otherPlayers.add((Player) o);
		}
		
		availableCards = findAvailableCards();
		availableNobles = b.getNobles();
		
		pq = new PriorityQueue<MapNode<Double, Card>> (new Comparator<MapNode<Double, Card>>() {
			@Override
			public int compare(MapNode<Double, Card> arg0, MapNode<Double, Card> arg1) {
				return Double.compare(arg0.getKey(), arg1.getKey());
			}
		});
		
		for (Card c : availableCards) {
			pq.add(new MapNode<Double, Card>(-1 * ValueCalculator.calculateCardValue(c, this, availableNobles, otherPlayers, availableCards, b.getCoins()), c));
		}
		
		for (Card c : this.getReserves()) {
			pq.add(new MapNode<Double, Card>(-1 * ValueCalculator.calculateCardValue(c, this, availableNobles, otherPlayers, availableCards, b.getCoins()), c));
		}
		
		topChoices = new Card[3];
		for (int i = 0; i < 3; i++) {
			MapNode<Double, Card> n = pq.poll();
			topChoices[i] = n.getValue();

		}
		
		for (int i = 0; i < 3; i++) {
			if (isPurchaseable(topChoices[i])) {
				purchase(topChoices[i]);
				returnCoinsToBank();
				purchaseNoble();
				checkEndCondition();
				return;
			}
		}
		
		if (canTakeThree()) {
			optimizeTakeThree();
			returnCoinsToBank();
			purchaseNoble();
			checkEndCondition();
			return;
		}
		
		if (canTakeTwo()) {
			optimizeTakeTwo();
			returnCoinsToBank();
			purchaseNoble();
			checkEndCondition();
			return;
		}
		
		if (canPurchaseAnything()) {
			purchaseAnything();
			returnCoinsToBank();
			purchaseNoble();
			checkEndCondition();
			return;
		}		
		
		if (canReserve()) {
			reserve();
			returnCoinsToBank();
			purchaseNoble();
			checkEndCondition();
			return;
		}
		
		if (canTakeOne()) {
			optimizeTakeOne();
			returnCoinsToBank();
			purchaseNoble();
			checkEndCondition();
			return;
		}
		
		returnCoinsToBank();
		purchaseNoble();
		checkEndCondition();
		return;
	}
	
	private boolean canReserve() {
		return this.getReserves().size() < 3;
	}
	
	private void reserve() {
		this.getReserves().add(topChoices[0]);
		if (b.getCoins()[5] > 0) {
			this.addCoins(5, 1);
			b.removeCoins(5, 1);
		}
	}
	
	private void purchaseAnything() {
		boolean purchase = false;
		while (!purchase) {
			Card c = pq.poll().getValue();
			if (isPurchaseable(c)) {
				purchase(c);
				purchase = true;
			}
		}
	}

	private boolean canPurchaseAnything() {
		for (Card c : availableCards) {
			if (isPurchaseable(c)) return true;
		}
		return false;
	}

	private boolean isMostCost(Card c, int x) {
		int[] cost = c.getCost().clone();
		
		for (int i = 0; i < 5; i++) {
			cost[i] = cost[i] - this.getCardTypes()[i] - this.getCoins()[i];
		}
		
		int val = cost[0];
		for (int i = 1; i < 5; i++) {
			if (cost[i] > val) val = cost[i];
		}
		
		return cost[x] == val;
	}
	
	private void optimizeTakeTwo() {

		PriorityQueue<MapNode<Integer, Double>> singles = new PriorityQueue<MapNode<Integer, Double>> (new Comparator<MapNode<Integer, Double>>() {
			@Override
			public int compare(MapNode<Integer, Double> arg0, MapNode<Integer, Double> arg1) {
				return (int) Double.compare(arg0.getValue(), arg1.getValue());
			}
		});
		
		PriorityQueue<MapNode<Integer, Double>> doubles = new PriorityQueue<MapNode<Integer, Double>> (new Comparator<MapNode<Integer, Double>>() {
			@Override
			public int compare(MapNode<Integer, Double> arg0, MapNode<Integer, Double> arg1) {
				return (int) Double.compare(arg0.getValue(), arg1.getValue());
			}
		});
		
		double value = 0;
		
		boolean[] validSingles = new boolean[]{false, false, false, false, false};
		boolean[] validDoubles = new boolean[]{false, false, false, false, false};
		for (int i = 0; i < 5; i++) {
			if (b.getCoins()[i] >= 4) validDoubles[i] = true;
			if (b.getCoins()[i] >= 1) validSingles[i] = true;
		}
		
		for (int i = 0; i < 5; i++) {
			if (validDoubles[i]) doubles.add(new MapNode<Integer, Double>(i, -1 * computeTakeTwoValue(i)));
		}
		
		for (int i = 0; i < 5; i++) {
			value = 0;
			
			if(topChoices[0].getCost()[i] >= 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
				value += (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
			}
			
			if(topChoices[1].getCost()[i] >= 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
				value += (0.7) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
			}
			
			if(topChoices[2].getCost()[i] >= 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
				value += (0.6) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
			}
		
			if (validSingles[i]) singles.add(new MapNode<Integer, Double>(i, -1 * value));
		}
		
		int[] singleChoices = new int[]{0,0,0,0,0};
		int[] doubleChoices = new int[]{0,0,0,0,0};
		
		double singleValue = 0;
		double doubleValue = 0;
		
		if (singles.size() >= 2){
			MapNode<Integer, Double> temp = singles.poll();
			singleValue += temp.getValue();
			singleChoices[temp.getKey()] = 1;
			
			temp = singles.poll();
			singleValue += temp.getValue();
			singleChoices[temp.getKey()] = 1;
		}
		
		if (doubles.size() >= 1) {
			MapNode<Integer, Double> temp = doubles.poll();
			doubleValue += (2 * temp.getValue());
			doubleChoices[temp.getKey()] = 2;
		}
		
		if (doubleValue >= singleValue) {
			takeCoinsGivenChoices(doubleChoices);	
		} else {
			takeCoinsGivenChoices(singleChoices);
		}
		
		
		
		
	}

	private boolean canTakeTwo() {
		int ans = 0;
		
		for (int i = 0; i < 5; i++) {
			if (b.getCoins()[i] >= 4) ans++;
		}
		
		int single = 0;
		for (int i = 0; i < 5; i++) {
			if (b.getCoins()[i] >= 1) single++;
		}
		
		return (ans >= 1 || single >= 2);
	}

	private LinkedList<Card> findAvailableCards() {
		LinkedList<Card> cards = new LinkedList<>();
		for (int i = 0; i < 4 && i < b.getFirst().size(); i++) {
			cards.add(b.getFirst().get(i));
		}
		
		for (int i = 0; i < 4 && i < b.getSecond().size(); i++) {
			cards.add(b.getSecond().get(i));
		}
		
		for (int i = 0; i < 4 && i < b.getThird().size(); i++) {
			cards.add(b.getThird().get(i));
		}
		
		for (Card c : this.getReserves()) {
			cards.add(c);
		}
		
		return cards;
	}
	
	private boolean isPurchaseable(Card c) {
		return ValueCalculator.turnsToGetMoney(c.getCost(), this.getCoins(), this.getCardTypes()) == 0;
	}
	
	private void purchase(Card c) {
		int[] coinCost = new int[5];
		for (int i = 0; i < 5; i++) {
			coinCost[i] = Math.max(c.getCost()[i] - this.getCardTypes()[i], 0);
		}
		
		int[] coinPayments = new int[]{0,0,0,0,0,0};
		for (int i = 0; i < 5; i++) {
			coinPayments[i] = Math.min(this.getCoins()[i], coinCost[i]);
			coinPayments[5] += coinCost[i] - coinPayments[i];
		}
		
		this.numCardTypes[c.getColor()] += 1;
		this.setVictoryPoints(this.getVictoryPoints() + c.getVictoryPoints());
		
		for (int i = 0; i < 6; i++) {
			this.addCoins(i, -1 * coinPayments[i]);
			b.removeCoins(i, -1 * coinPayments[i]);
		}
		
		b.remove(c);
	}
	
	private boolean isNoblePurchaseable(Noble n) {
		for (int i = 0; i < 5; i++) {
			if (n.getCost()[i] - this.getCardTypes()[i] > 0) return false;
		}
		return true;
	}
	
	private void purchaseNoble() {
		for (int i = 0; i < b.getNobles().size(); i++) {
			if (isNoblePurchaseable(b.getNobles().get(i))) {
				b.removeNoble(i);
				this.setNobles(this.getNobles() + 1);
				this.setVictoryPoints(this.getVictoryPoints() + 3);
				return;
			}
		}
	}
	
	private boolean canTakeThree() {
		int ans = 0;
		for (int i = 0; i < 5; i++) {
			if (b.getCoins()[i] > 0) ans++;
		}
		return ans >= 3;
	}
	
	private void optimizeTakeThree() {
		
		PriorityQueue<MapNode<Integer, Double>> colorVals = new PriorityQueue<MapNode<Integer, Double>> (new Comparator<MapNode<Integer, Double>>() {
			@Override
			public int compare(MapNode<Integer, Double> arg0, MapNode<Integer, Double> arg1) {
				return (int) (100 * (arg0.getValue() - arg1.getValue()));
			}
		});
		
		double[] values = new double[5];
		int[] cost1 = new int[5];
		int[] cost2 = new int[5];
		int[] cost3 = new int[5];
		for (int i = 0; i < 5; i++) {
			cost1[i] = Math.max(topChoices[0].getCost()[i] - this.getCardTypes()[i] - this.getCoins()[i], 0);
			cost2[i] = Math.max(topChoices[1].getCost()[i] - this.getCardTypes()[i] - this.getCoins()[i], 0);
			cost3[i] = Math.max(topChoices[2].getCost()[i] - this.getCardTypes()[i] - this.getCoins()[i], 0);
			
			values[i] = 100 * cost1[i] + 10 * cost2[i] + cost3[i];
			if(b.getCoins()[i] == 0) values[i] = -5000;
		}
		
		
		for (int i = 0; i < 5; i++) {
			colorVals.add(new MapNode<Integer, Double>(i, -1 * values[i]));
		}
		
		int[] choices = new int[]{0,0,0,0,0};
		for (int i = 0; i < 3; i++) {
			choices[colorVals.poll().getKey()] = 1;
		}
		
		takeCoinsGivenChoices(choices);
	}

	private void takeCoinsGivenChoices(int[] choices) {
		
		if (choicesNotValid(choices)) {
			for (int i : choices) {
				System.out.print(i + "   ");
			}
			throw new IllegalStateException("AI is trying to take an illegal amount of coins");
		}
		
		for (int i = 0; i < 5; i++) {
				b.getCoins()[i] -= choices[i];
				this.getCoins()[i] += choices[i];
		}
	}

	private void checkEndCondition() {
		if (this.getVictoryPoints() >= 15) {
			game.setEndReached(true);
			game.setTurnsRemaining(handler.getObjects().size() - 1);
		}
		if (game.getEndReached()) {
			game.setTurnsRemaining(game.getTurnsRemaining() - 1);
		}
	}

	private int boolToInt(boolean b) {
		if (b) return 1;
		return 0;
	}
	
	private boolean choicesNotValid(int[] a) {
		boolean ans = true;
	
		for (int i = 0; i < 5; i++) {
			ans = ans && (b.getCoins()[i] - a[i] >= 0);
		}
		
		return !ans;
	}
	
	private int numTrue(boolean[] a) {
		int i = 0;
		for (boolean b : a) {
			if (b) i++;
		}
		return i;
	}
	
	private double computeTakeTwoValue(int i) {
		double val = 0;
		
		if (topChoices[0].getCost()[i] >= 2 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		} else if (topChoices[0].getCost()[i] == 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.8) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		}
		
		if (topChoices[1].getCost()[i] >= 2 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.8) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		} else if (topChoices[1].getCost()[i] == 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.5) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		}
		
		if (topChoices[2].getCost()[i] >= 2 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.7) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		} else if (topChoices[2].getCost()[i] == 1 + this.getCoins()[i] + this.getCardTypes()[i]) {
			val += (0.4) * (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
		}
		
		return val;
	}

	private void returnCoinsToBank() {
		//While we have more coins than 10, we will remove the least useful
		PriorityQueue<MapNode<Integer, Double>> colorVals = new PriorityQueue<MapNode<Integer, Double>> (new Comparator<MapNode<Integer, Double>>() {
			@Override
			public int compare(MapNode<Integer, Double> arg0, MapNode<Integer, Double> arg1) {
				return (int) Double.compare(arg0.getValue(), arg1.getValue());
			}
		});
		
		while (this.getNumCoins() > 10) {
			
			for (int i = 0; i < 5; i++) {
				
				if (coins[i] > 0) {
					double value = 0;
					
					if (topChoices[0].getCost()[i] > 0) {
						value += (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
					}
					if (topChoices[1].getCost()[i] > 0) {
						value += (0.7) * (0.5) * (1 + boolToInt(isMostCost(topChoices[1], i)));
					}
					if (topChoices[2].getCost()[i] > 0) {
						value += (0.6) * (0.5) * (1 + boolToInt(isMostCost(topChoices[2], i)));
					}
					
					colorVals.add(new MapNode<Integer, Double>(i, value));
				}
			}
			
			int col = colorVals.poll().getKey();
			b.removeCoins(col, -1);
			this.addCoins(col, -1);

		}
	}

	private boolean canTakeOne() {
		for (int i : b.getCoins()) {
			if (i > 0) return true;
		}
		return false;
	}
	
	private void optimizeTakeOne() {
		
		PriorityQueue<MapNode<Integer, Double>> colorVals = new PriorityQueue<MapNode<Integer, Double>> (new Comparator<MapNode<Integer, Double>>() {
			@Override
			public int compare(MapNode<Integer, Double> arg0, MapNode<Integer, Double> arg1) {
				return (int) Double.compare(arg0.getValue(), arg1.getValue());
			}
		});
		
		int[] choices = new int[5];
		
		for (int i = 0; i < 5; i++) {
			if (b.getCoins()[i] > 0) {
				double value = 0;
				
				if (topChoices[0].getCost()[i] > 0) {
					value = (0.5) * (1 + boolToInt(isMostCost(topChoices[0], i)));
				}
				if (topChoices[1].getCost()[i] > 0) {
					value = (0.7) * (0.5) * (1 + boolToInt(isMostCost(topChoices[1], i)));
				}
				if (topChoices[2].getCost()[i] > 0) {
					value = (0.6) * (0.5) * (1 + boolToInt(isMostCost(topChoices[2], i)));
				}
				
				colorVals.add(new MapNode<Integer, Double>(i, -1 * value));
			}
			
			choices[colorVals.poll().getKey()] = 1;
			takeCoinsGivenChoices(choices);
		}
		
	}
}
