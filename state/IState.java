package main.state;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.manager.Handler;

public abstract class IState{
	
	protected Handler handler;
	protected Graphics g;
	
	public IState(Handler handler){
		this.handler=handler;
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void handleInput();
	public abstract void debug(Graphics2D g);
	public abstract void dispose();
	public abstract void onEnter();
	public abstract void onExit();
}