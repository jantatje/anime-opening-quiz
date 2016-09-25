package main.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.data.Song;
import main.gui.ClickListener;
import main.gui.TextButton;
import main.gui.UIManager;
import main.manager.Handler;
import main.util.ImageLoader;

public class ResultState extends IState{

	public Song correctSong;
	public boolean youWin;
	private UIManager uiManager;
	private TextButton buttonBack, buttonNext;
	
	public ResultState(Handler handler, boolean animeIsRight, Song chosenSong) {
		super(handler);
		uiManager = new UIManager(handler);
		this.youWin=animeIsRight;
		this.correctSong=chosenSong;
		if(animeIsRight){
			int streak = handler.getPrefs().getCurrStreak();
			handler.getPrefs().setCurrStreak(streak+1);
		}else{
			handler.getPrefs().setCurrStreak(0);
		}
		if(handler.getPrefs().getCurrStreak()>handler.getPrefs().getBestStrak()){
			handler.getPrefs().setBestStrak(handler.getPrefs().getCurrStreak());
		}
	}

	@Override
	public void init() {
		handler.getMouseManager().setUIManager(uiManager);
		
//Button Images
		BufferedImage button_1=ImageLoader.loadImageIn("/graphics/gui/Button_default_blue.png");
		BufferedImage[]buttons = new BufferedImage[2];
		buttons[0]=button_1;
		buttons[1]=button_1;
//GUI Stuff
		final int space=40;
		float scale = 2f;
		final int buttonWidth=(int)(button_1.getWidth()*scale),buttonHeight=(int)(button_1.getHeight()*scale);
		final int XPos=(handler.getWidth()-buttonWidth)/2,YPos=(handler.getHeight()+buttonHeight)/2;
				
		//GUI
		buttonNext=new TextButton(XPos, YPos, buttonWidth, buttonHeight, "Next");
		buttonNext.setImages(buttons);
		buttonNext.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				handler.getStateManager().push(new GameState(handler));
				handler.getStateManager().stack.remove(0);
		}});
		buttonBack=new TextButton(XPos, YPos+buttonHeight+space, buttonWidth, buttonHeight, "Menu");
		buttonBack.setImages(buttons);
		buttonBack.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				handler.getStateManager().push(new MainMenuState(handler));
				handler.getStateManager().stack.remove(0);
		}});
		
		uiManager.addObject(buttonNext);
		uiManager.addObject(buttonBack);
		
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		if(youWin){
			
			drawStringXMiddle(g, "Congratulation!!", "Arial", Color.GREEN, Font.BOLD, 40, handler.getWidth(), 70);
		} else {
			drawStringXMiddle(g, "You're wrong!!", "Arial", Color.RED, Font.BOLD, 40, handler.getWidth(), 70);
		}
		drawStringXMiddle(g, "Current Streak: "+handler.getPrefs().getCurrStreak(), "Arial", Color.BLACK, Font.PLAIN, 40, handler.getWidth(), 150);
		
		drawStringXMiddle(g, "Anime: "+correctSong.AnimeName, "Arial", Color.BLACK, Font.BOLD, 30, handler.getWidth(), 250);
		
		drawStringXMiddle(g, "Interpret: "+correctSong.Interpret, "Arial", Color.BLACK, Font.BOLD, 30, handler.getWidth(), 350);
		
		drawStringXMiddle(g, "Title: "+ correctSong.Title, "Arial", Color.BLACK, Font.BOLD, 30, handler.getWidth(), 400);
		
		
		uiManager.render(g);
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void debug(Graphics2D g) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onExit() {
		
	}
	@SuppressWarnings("unused")
	private void drawString(Graphics g, String text, String font, Color color, int prop, int maxSize, int maxWidth, int x, int y){
		g.setColor(color);
		g.setFont(new Font(font,prop,maxSize));
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		
		if(textWidth>maxWidth){
			for(int textSize=maxSize;textWidth>maxWidth;textSize--){
			g.setFont(new Font(font,prop,textSize));
			fm = g.getFontMetrics();
			textWidth = fm.stringWidth(text);
			}
		}
		g.drawString(text, x, y);
	}
	
	private void drawStringXMiddle(Graphics g, String text, String font, Color color, int prop, int maxSize, int maxWidth, int y){
		g.setColor(color);
		g.setFont(new Font(font,prop,maxSize));
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		
		if(textWidth>maxWidth){
			for(int textSize=maxSize;textWidth>maxWidth;textSize--){
			g.setFont(new Font(font,prop,textSize));
			fm = g.getFontMetrics();
			textWidth = fm.stringWidth(text);
			}
		}
		g.drawString(text, (handler.getWidth()-textWidth)/2, y);
	}
}
