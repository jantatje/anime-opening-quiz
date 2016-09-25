package main.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import main.gui.ClickListener;
import main.gui.TextButton;
import main.gui.UIManager;
import main.manager.Handler;
import main.util.ImageLoader;

public class MainMenuState extends IState{
	@SuppressWarnings("unused")
	private Font comicSansMS_default;
	
	private TextButton buttonStart;
	private TextButton buttonOptions;
	private TextButton buttonQuit;
	
	private UIManager uiManager;
	
	public MainMenuState(final Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
	}
	
	
	@Override
	public void init() {
		
		handler.getMouseManager().setUIManager(uiManager);
		//Button Images
		BufferedImage button_1=ImageLoader.loadImageIn("/graphics/gui/Button_default_blue.png");
		BufferedImage[]buttons = new BufferedImage[2];
		buttons[0]=button_1;
		buttons[1]=button_1;
		
		//Font
		try {
			InputStream is = MainMenuState.class.getResourceAsStream("/font/ComicSansMS/comic.ttf");
			comicSansMS_default = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		final int space=40;
		float scale = 2f;
		final int buttonWidth=(int)(button_1.getWidth()*scale),buttonHeight=(int)(button_1.getHeight()*scale);
		final int XPos=(handler.getWidth()-buttonWidth)/2,YPos=(handler.getHeight()-buttonHeight)/2;
		
		
		//GUI
		buttonStart=new TextButton(XPos, YPos, buttonWidth, buttonHeight, "Start");
		buttonStart.setFontSize(40);
		buttonStart.setImages(buttons);
		buttonStart.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				onExit();
				handler.getMouseManager().setUIManager(null);
				handler.getStateManager().push(new GameState(handler));
				handler.getStateManager().stack.remove(0);
		}});
		buttonOptions=new TextButton(XPos, YPos+buttonHeight+space, buttonWidth, buttonHeight, "Options");
		buttonOptions.setFontSize(40);
		buttonOptions.setImages(buttons);
		buttonOptions.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				handler.getStateManager().push(new OptionsState(handler));
				handler.getStateManager().stack.remove(0);
		}});
		buttonQuit=new TextButton(XPos, YPos+2*buttonHeight+2*space, buttonWidth, buttonHeight, "Quit");
		buttonQuit.setFontSize(40);
		buttonQuit.setImages(buttons);
		buttonQuit.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				System.exit(1);
		}});
		
		uiManager.addObject(buttonStart);
		uiManager.addObject(buttonOptions);
		uiManager.addObject(buttonQuit);
	}
	
	@Override
	public void tick() {
		handleInput();
	}

	@Override
	public void render(Graphics g) {
		drawStringXMiddle(g, "Anime Opening Quiz", "Arial", Color.BLACK, Font.BOLD, 50, 200, 100);
		drawStringXMiddle(g, "Current Streak: "+handler.getPrefs().getCurrStreak(), "Arial", Color.BLACK, Font.PLAIN, 30, 200, 200);
		drawStringXMiddle(g, "Best Streak: "+handler.getPrefs().getBestStrak(), "Arial", Color.BLACK, Font.PLAIN, 30, 200, 300);
		
		uiManager.render(g);
	}
	
	
	@Override
	public void debug(Graphics2D g) {
		
	}


	@Override
	public void handleInput() {
		uiManager.tick();
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
		
		g.drawString(text, (handler.getWidth()-textWidth)/2, y);
	}
}
