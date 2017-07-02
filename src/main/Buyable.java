package main;

public abstract class Buyable {
	protected int[] cost;
	protected int victoryPoints;
	
	public Buyable(int bk, int bl, int g, int r, int w, int vp) {
		cost = new int[] {bk, bl, g, r, w};
		victoryPoints = vp;
	}
	
	public int[] getCost() {
		return cost;
	}
	
	public int getVictoryPoints() {
		return victoryPoints;
	}
}
