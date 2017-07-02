package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

public class ValueCalculator {

	/** How valuable would the card be to the player at the current time. */
	public static double calculateCardValue(Card c, Player p, LinkedList<Noble> nobles, LinkedList<Player> otherPlayers, LinkedList<Card> cards, int[] bank) {
		double nobleValue = 0;
		double opponent = 0;
		for (Noble n : nobles) {
			nobleValue += computeNobleModifier(c, n, p);
		}
		
		for(Player o : otherPlayers) {
				opponent += computeOpponentModifier(c, o, cards, nobles);
		}

		nobleValue = nobleValue / nobles.size();
		double ppt = computePointsPerTurnModifier(c, p);
		double under = computeUnderRepresentedModifier(c, p);
		double purchaseable = computePurchaseableModifier(c, bank, p.getCoins(), p.getCardTypes());
		opponent = opponent / otherPlayers.size();
		
		return nobleValue + ppt + under - opponent - purchaseable;
	}
	
	public static double computeOpponentModifier(Card c, LinkedList<Player> opp, LinkedList<Card> cards, LinkedList<Noble> nobles) {
		double ans = 0;
		for (Player o : opp) {
			ans += computeOpponentModifier(c, o, cards, nobles);
		}
		return ans / opp.size();
	}
	
	public static double computeNobleModifier(Card c, LinkedList<Noble> nobles, Player p) {
		double ans = 0;
		for (Noble n : nobles) {
			ans += computeNobleModifier(c, n, p);
		}
		return ans / nobles.size();
	}
	
	public static double computePointsPerTurnModifier(Card c, Player p) {
		return ((double) c.victoryPoints) / (turnsToGetMoney(c.cost, p.coins, p.numCardTypes) + 1);
	}

	public static int turnsToGetMoney(int[] cost, int[] coins, int[] numCardTypes) {
		int ans = 0;
		int[] updatedCost = new int[5];
		updatedCost[0] = Math.max(cost[0] - coins[0] - numCardTypes[0], 0);
		updatedCost[1] = Math.max(cost[1] - coins[1] - numCardTypes[1], 0);
		updatedCost[2] = Math.max(cost[2] - coins[2] - numCardTypes[2], 0);
		updatedCost[3] = Math.max(cost[3] - coins[3] - numCardTypes[3], 0);
		updatedCost[4] = Math.max(cost[4] - coins[4] - numCardTypes[4], 0);
		
		int gold = coins[5];
		
		while (gold > 0) {
			gold--;
			Arrays.sort(updatedCost);
			updatedCost[4]--;
		}
		Arrays.sort(updatedCost);
		ans = updatedCost[4];
		int leftover = updatedCost[4] * 2;
		updatedCost[4] = 0;
		int index = 3;
		while (index >= 0) {
			int temp = Math.min(leftover, updatedCost[index]);
			leftover -= temp;
			updatedCost[index] -= temp;
			if(leftover > 0) index--;
			else {
				ans += updatedCost[index];
				leftover = updatedCost[index] * 2;
				updatedCost[index] = 0;
				index--;
			}
		}
		return ans;
	}

	public static double computeOpponentModifier(Card c, Player opp, LinkedList<Card> cards, LinkedList<Noble> nobles) {
		double pmax = 0;
		double temp;
		
		for(Card card : cards) {
			double denom = (1 + turnsToGetMoney(card.getCost(), opp.getCoins(), opp.getCardTypes()));
			
			if (scoresNoble(card, opp.getCardTypes(), nobles)) {
				temp = (3 + card.getVictoryPoints()) / denom;
			} else {
				temp = card.getVictoryPoints() / denom;
			}
			
			if (temp > pmax) pmax = temp;
		}
		
		double denom = (1 + turnsToGetMoney(c.cost, opp.getCoins(), opp.getCardTypes()));
		
		if(scoresNoble(c, opp.getCardTypes(), nobles)) {
			return ((c.getVictoryPoints() + 3) / denom) / pmax;
		} else {
			return (c.getVictoryPoints() / denom) / pmax;
		}	
	}

	public static double computeUnderRepresentedModifier(Card c, Player p) {
		int[] temp;
		int numColor = p.numCardTypes[c.getColor()];
		Arrays.sort(temp = p.numCardTypes.clone());
		int numMax = temp[temp.length - 1];
		return Math.pow(1.08, numMax - numColor) - 1;
	}

	public static double computeNobleModifier(Card c, Noble n, Player p) {
		int[] updatedCost = new int[5];
		double remain = 0;
		int tot = 0;
		
		for (int i = 0; i < 5; i++) {
			updatedCost[i] = Math.max(0, n.getCost()[i] - p.getCardTypes()[i]);
		}
		
		if (updatedCost[c.getColor()] <= 0) return 0;
		
		for (int i : updatedCost) {
			remain += i;
		}
		
		for (int i : n.cost) {
			tot += i;
		}
		
		return Math.pow((tot - remain + 1) / tot, 4);
		
	}

	public static boolean scoresNoble(Card c, int[] types, LinkedList<Noble> nobles) {
		for (Noble n : nobles) {
			if (scoresNoble(c, types, n)) return true;
		}
		return false;
	}
	
	public static boolean scoresNoble(Card c, int[] types, Noble n) {
		for (int i = 0; i < 5; i++) {
			if (i == c.getColor()) {
				if (n.getCost()[i] - types[i] != 1) return false;
			} else {
				if (n.getCost()[i] - types[i] > 0) return false;
			}
		}
		return true;
	}

	public static double computePurchaseableModifier(Card c, int[] bank, int[] coins, int[] cards) {
		double turns = turnsToGetMoney(c.getCost(), coins, cards);
		int[] neededCoins = new int[5];
		for (int i = 0; i < 5; i++) {
			neededCoins[i] = Math.max(c.getCost()[i] - coins[i] - cards[i], 0); 
		}
		int failures = 0;
		for (int i = 0; i < 5; i++) {
			if (bank[i] < (int) 1.5 * neededCoins[i]) failures++;
		}
		double[] results = new double[]{1.5, 2, 2.75, 4, 6};
		
		return results[failures] + tierModifier(c, cards);
	}
	
	public static double tierModifier(Card c, int[] cards) {
		int[] updatedCost = new int[5];
		for (int i = 0; i < 5; i++) {
			updatedCost[i] = Math.max(0, c.getCost()[i] - cards[i]);
		}
		int numCoins = 0;
		for (int i : updatedCost) {
			numCoins += i;
		}
		
		double coinMod = 0;
		if (numCoins == 5) {
			coinMod = 0.2;
		} else if (numCoins == 6) {
			coinMod = 0.4;
		} else if (numCoins == 7) {
			coinMod = 0.7;
		} else if (numCoins == 8) {
			coinMod = 1.1;
		} else if (numCoins == 9) {
			coinMod = 1.5;
		}
		
		double indivMod = 0;
		for (int i : updatedCost) {
			if (i == 3) {
				indivMod += 0.1;
			} else if (i == 4) {
				indivMod += 0.5;
			} else if (i == 5) {
				indivMod += 0.9;
			} else if (i == 6) {
				indivMod = 1.4;
			} else if (i == 7) {
				indivMod = 1.9;
			}
		}
		
		if (c.getTier() == 1) return 2 + coinMod + indivMod;
		return coinMod + indivMod;
	}
	
	@Test
	public void testTurnsToGetMoney() {
		int[] cost = new int[]{1,1,1,0,0};
		int[] coins = new int[]{0,0,0,0,0,0};
		int[] cards = new int[]{0,0,0,0,0};
		assertEquals("Test 1", 1, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,0,0};
		coins = new int[]{1,1,0,1,0,0};
		cards = new int[]{0,0,0,0,0};
		assertEquals("Test 2", 1, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,0,0};
		coins = new int[]{1,1,1,0,0,0};
		cards = new int[]{0,0,0,0,0};
		assertEquals("Test 3", 0, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{2,1,1,0,0};
		coins = new int[]{0,0,0,0,0,0};
		cards = new int[]{0,0,0,0,0};
		assertEquals("Test 4", 2, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{2,1,1,0,0};
		coins = new int[]{0,0,0,0,0,0};
		cards = new int[]{1,0,0,0,0};
		assertEquals("Test 5", 1, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{3,3,0,3,5};
		coins = new int[]{1,0,1,1,0,0};
		cards = new int[]{2,0,1,0,0};
		assertEquals("Test 6", 5, turnsToGetMoney(cost, coins, cards));	
		
		cost = new int[]{0,3,6,3,0};
		coins = new int[]{0,0,1,1,1,0};
		cards = new int[]{0,0,0,4,0};
		assertEquals("Test 7", 5, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{0,0,0,7,0};
		coins = new int[]{1,2,0,0,1,0};
		cards = new int[]{3,0,0,0,3};
		assertEquals("Test 8", 7, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{0,0,0,0,0,0};
		cards = new int[]{0,0,0,0,0};
		assertEquals("Test 9", 2, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{1,1,1,1,1,0};
		cards = new int[]{0,0,0,0,0};
		assertEquals("Test 10", 0, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{0,0,0,0,0,0};
		cards = new int[]{1,1,1,1,1};
		assertEquals("Test 11", 0, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{1,1,0,0,0,0};
		cards = new int[]{0,0,1,1,1};
		assertEquals("Test 12", 0, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{0,0,0,1,1,1};
		cards = new int[]{1,1,0,0,0};
		assertEquals("Test 13", 0, turnsToGetMoney(cost, coins, cards));
		
		cost = new int[]{1,1,1,1,1};
		coins = new int[]{0,0,0,0,0,5};
		cards = new int[]{1,1,0,0,0};
		assertEquals("Test 14", 0, turnsToGetMoney(cost, coins, cards));
		
		
	}

	@Test
	public void testScoresNoble() {
		Card c;
		Noble n;
		int[] types;
		
		c = new Card(0,0,0,0,0,0,0,0);
		n = new Noble(0,3,3,3,0);
		types = new int[]{0,3,3,3,0};
		assertEquals("Test 1", false, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,1,0,0);
		n = new Noble(0,4,0,0,4);
		types = new int[]{0,4,3,3,0};
		assertEquals("Test 2", false, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,0,0,0);
		n = new Noble(4,0,4,0,0);
		types = new int[]{3,0,3,0,0};
		assertEquals("Test 3", false, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,0,0,0);
		n = new Noble(3,0,0,3,3);
		types = new int[]{3,3,3,3,3};
		assertEquals("Test 4", false, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,0,0,0);
		n = new Noble(0,4,4,0,0);
		types = new int[]{0,4,4,3,0};
		assertEquals("Test 5", false, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,1,0,0);
		n = new Noble(0,3,3,0,3);
		types = new int[]{0,2,3,0,3};
		assertEquals("Test 6", true, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,3,0,0);
		n = new Noble(4,0,0,4,0);
		types = new int[]{5,0,0,3,0};
		assertEquals("Test 7", true, scoresNoble(c, types, n));
		
		c = new Card(0,0,0,0,0,4,0,0);
		n = new Noble(0,0,0,4,4);
		types = new int[]{3,3,3,4,3};
		assertEquals("Test 8", true, scoresNoble(c, types, n));
	}

	
}
