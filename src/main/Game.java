package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 6691247796639148462L;
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	
	public MainMenu mainMenu;
	public HumanTurnMenu humanTurnMenu;
	public actions.OpponentView oppView;
	public actions.Take2 twoView;
	public actions.Take3 threeView;
	public actions.Buy buyView;
	public actions.ReservesView reservesView;
	public actions.ToBank toBankView;
	public actions.HumanActionTaken actionTaken;
	public actions.Purchase purchaseView;
	public actions.AITurn aiView;
	public actions.NobleVisit nobleView;
	public actions.GameOver gameOverView;
	
	private boolean endReached;
	private int turnsRemaining;
	
	public enum STATE {
		Main, //Main menu
		Game, //Give the player options for taking coins / buying cards
		ViewOpp, //View what the opponent has
		Take3, //Take 3 coins view
		Take2, //Take 2 coins view
		Buy, //Purchase or reserve a card view
		Reserves, //View current players' reserved cards
		ToBank, // Give coins back to bank
		AITurn,  //Take all AI turns
		ActionTaken, //After Human has taken main action
		Purchase, //Spend Coins to get a card
		Noble, //Nobles Come and Visit
		GameOver, //Game is Over
		Reset
	};
	
	public STATE gameState = STATE.Main;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		handler = new Handler();
		this.setFocusable(true);
		
		//Creates the window and starts the game
		new Window(WIDTH, HEIGHT, "Splendor", this);
		
		mainMenu = new MainMenu(this, handler);
		humanTurnMenu = new HumanTurnMenu(this, handler);
		oppView = new actions.OpponentView(this, handler);
		twoView = new actions.Take2(this, handler);
		threeView = new actions.Take3(this, handler);
		buyView = new actions.Buy(this, handler);
		reservesView = new actions.ReservesView(this, handler);
		toBankView = new actions.ToBank(this, handler);
		actionTaken = new actions.HumanActionTaken(this, handler);
		purchaseView = new actions.Purchase(this, handler);
		aiView = new actions.AITurn(this, handler);
		nobleView = new actions.NobleVisit(this, handler);
		gameOverView = new actions.GameOver(this, handler);
		
		this.addMouseListener(mainMenu);
		if (this.getMouseListeners().length != 1) {
			throw new IllegalStateException("More than one MouseListener");
		}
		endReached = false;
		turnsRemaining = -1;
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			
			if(running) {
				render();
			}
			
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		if(gameState == STATE.Game) {
			humanTurnMenu.tick();
		} else if (gameState == STATE.Main) {
			mainMenu.tick();
		} else if (gameState == STATE.ViewOpp) {
			oppView.tick();
		} else if (gameState == STATE.Take3) {
			threeView.tick();
		} else if (gameState == STATE.Take2) {
			twoView.tick();
		} else if (gameState == STATE.Buy) {
			buyView.tick();
		} else if (gameState == STATE.Reserves) {
			reservesView.tick();
		} else if (gameState == STATE.ToBank) {
			toBankView.tick();
		} else if (gameState == STATE.AITurn) {
			aiView.tick();
		} else if (gameState == STATE.ActionTaken) {
			actionTaken.tick();
		} else if (gameState == STATE.Purchase) {
			purchaseView.tick();
		} else if (gameState == STATE.Noble) {
			nobleView.tick();
		} else if (gameState == STATE.GameOver) {
			gameOverView.tick();
		} else if (gameState == STATE.Reset) {
			reset();
			gameState = STATE.Main;
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(210, 180, 140));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameState == STATE.Game) {
			humanTurnMenu.render(g);
			handler.render(g);
			
		} else if (gameState == STATE.Main) {
			mainMenu.render(g);
		} else if (gameState == STATE.ViewOpp) {
			oppView.render(g);
		} else if (gameState == STATE.Take3) {
			threeView.render(g);
		} else if (gameState == STATE.Take2) {
			twoView.render(g);
		} else if (gameState == STATE.Buy) {
			buyView.render(g);
		} else if (gameState == STATE.Reserves) {
			reservesView.render(g);
		} else if (gameState == STATE.ToBank) {
			toBankView.render(g);
		} else if (gameState == STATE.AITurn) {
			aiView.render(g);
		} else if (gameState == STATE.ActionTaken) {
			actionTaken.render(g);
		} else if (gameState == STATE.Purchase) {
			purchaseView.render(g);
		} else if (gameState == STATE.Noble) {
			nobleView.render(g);
		} else if (gameState == STATE.GameOver) {
			gameOverView.render(g);
		} else if (gameState == STATE.Reset) {
			
		}
		
		g.dispose();
		bs.show();
	}

	private void reset() {
		handler = new Handler();
		mainMenu = new MainMenu(this, handler);
		humanTurnMenu = new HumanTurnMenu(this, handler);
		oppView = new actions.OpponentView(this, handler);
		twoView = new actions.Take2(this, handler);
		threeView = new actions.Take3(this, handler);
		buyView = new actions.Buy(this, handler);
		reservesView = new actions.ReservesView(this, handler);
		toBankView = new actions.ToBank(this, handler);
		actionTaken = new actions.HumanActionTaken(this, handler);
		purchaseView = new actions.Purchase(this, handler);
		aiView = new actions.AITurn(this, handler);
		nobleView = new actions.NobleVisit(this, handler);
		this.addMouseListener(mainMenu);
		if (this.getMouseListeners().length != 1) {
			throw new IllegalStateException("More than one MouseListener");
		}
		endReached = false;
		turnsRemaining = -1;
	}

	public boolean getEndReached() {
		return endReached;
	}
	
	public void setEndReached(boolean b) {
		endReached = b;
	}
	
	public void setTurnsRemaining(int i) {
		turnsRemaining = i;
	}
	
	public int getTurnsRemaining() {
		return turnsRemaining;
	}

}
