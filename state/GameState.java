package main.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import main.data.Anime;
import main.data.Song;
import main.gui.ClickListener;
import main.gui.TextButton;
import main.gui.UIManager;
import main.manager.Handler;
import main.util.AudioPlayer;
import main.util.ImageLoader;

public class GameState extends IState{
	
	private Anime Anime1,Anime2,Anime3;
	
	private Anime chosenAnime;
	private Song chosenSong;
	
	private AudioPlayer currentSong;
	
	private ArrayList<Song> allSongList = handler.getSongList();
	private ArrayList<Anime> allAnimeList = handler.getAnimeList();
	private ArrayList<Song> chosenAnimeSongs = new ArrayList<Song>();
	
	private TextButton Button_Top,Button_Middle,Button_Bottom;
	
	private UIManager uiManager;
	
	public GameState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
	}

	@Override
	public void init() {
		handler.getMouseManager().setUIManager(uiManager);
		//Button Images
		BufferedImage button_1=ImageLoader.loadImageIn("/graphics/gui/Button_Game.png");
		BufferedImage[]buttons = new BufferedImage[2];
		buttons[0]=button_1;
		buttons[1]=button_1;
		
		//Anime Song Stuff
		if(handler.lastExists()){
			chosenSong = handler.getPrefs().getLastsong();
			Anime1 = handler.getPrefs().getAnime1();
			Anime2 = handler.getPrefs().getAnime2();
			Anime3 = handler.getPrefs().getAnime3();
		}else{
			pick3RandomAnime();
			chosenAnime = chooseOneAnime();
			//System.out.println("Chosen Anime: "+chosenAnime.AnimeName);
			getSongs();
			chosenSong = chooseSong();
			//System.out.println("Chosen Song: "+chosenSong.FileName);
		}
		
		handler.getPrefs().setAnime1(Anime1);
		handler.getPrefs().setAnime2(Anime2);
		handler.getPrefs().setAnime3(Anime3);
		handler.getPrefs().setLastSong(chosenSong);
		handler.getPrefs().setLastExists(true);

		//GUI Stuff
		final int space=50;//space between Buttons
		final int buttonWidth=handler.getWidth()/3*2,buttonHeight=handler.getHeight()/12;
		final int XPos=handler.getWidth()/2-buttonWidth/2,YPos=(handler.getHeight()-buttonHeight)/2;
		//GUI Init
		Button_Top=new TextButton(XPos, YPos, buttonWidth, buttonHeight, Anime1.AnimeName);
		Button_Top.setImages(buttons);
		Button_Top.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				toResultState(Anime1.AnimeName.equals(chosenSong.AnimeName));
		}});
		Button_Middle=new TextButton(XPos, YPos+buttonHeight+space, buttonWidth, buttonHeight, Anime2.AnimeName);
		Button_Middle.setImages(buttons);
		Button_Middle.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				toResultState(Anime2.AnimeName.equals(chosenSong.AnimeName));
		}});
		Button_Bottom=new TextButton(XPos, YPos+2*buttonHeight+2*space, buttonWidth, buttonHeight, Anime3.AnimeName);
		Button_Bottom.setImages(buttons);
		Button_Bottom.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				toResultState(Anime3.AnimeName.equals(chosenSong.AnimeName));
		}});
		
		uiManager.addObject(Button_Top);
		uiManager.addObject(Button_Middle);
		uiManager.addObject(Button_Bottom);
		
		//Music Player		
		currentSong = new AudioPlayer(chosenSong.FileName);
		currentSong.start();
		currentSong.setVolume(0.5f);
		currentSong.setLooping(true);
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		
		drawStringXMiddle(g, "Current Streak: "+handler.getPrefs().getCurrStreak(), "Arial", Color.BLACK, Font.PLAIN, 40, 200, 200);
		
		uiManager.render(g);
	}
	
	private void drawStringXMiddle(Graphics g, String text, String font, Color color, int prop, int maxSize, int maxWidth, int y){
		g.setColor(color);
		g.setFont(new Font(font,prop,maxSize));
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		
		g.drawString(text, (handler.getWidth()-textWidth)/2, y);
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void debug(Graphics2D g) {
		
	}

	@Override
	public void dispose() {
		currentSong.close();
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onExit() {
		currentSong.close();
	}
	
	public void pick3RandomAnime(){
		Collections.shuffle(allAnimeList);
		Collections.shuffle(allAnimeList);
		Anime1 = allAnimeList.get(0);
		Anime2 = allAnimeList.get(1);
		Anime3 = allAnimeList.get(2);
	}
	private Anime chooseOneAnime() {
		Random random = new Random();
		int rand = (random.nextInt(30)-1)/10;
		switch(rand){
		case 0:return Anime1;
		case 1:return Anime2;
		case 2:return Anime3;
		}
		return null;
		
	}
	private ArrayList<Song> getSongs() {
		for(int i=0;i<allSongList.size();i++){
			if(allSongList.get(i).AnimeName.equals(chosenAnime.AnimeName)){
				chosenAnimeSongs.add(allSongList.get(i));
			}
		}
		return chosenAnimeSongs;
	}
	
	private Song chooseSong() {
		Collections.shuffle(chosenAnimeSongs);
		return chosenAnimeSongs.get(0);
	}
	private void toResultState(boolean correct){
		handler.setLastExists(false);
		handler.getMouseManager().setUIManager(null);
		onExit();
		dispose();
		handler.getStateManager().push(new ResultState(handler,correct,chosenSong));
		handler.getStateManager().stack.remove(0);
	}

}
