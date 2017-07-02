package main;

import java.awt.Graphics;

public abstract class GameObject {

	protected ID id;
	protected Game game;
	
	public GameObject(ID id, Game game) {
		this.id = id;
		this.game = game;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setID(ID id) {
		this.id = id;
	}
	
	public ID getID() {
		return id;
	}
	
}
