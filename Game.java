package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import main.data.Anime;
import main.data.Song;
import main.launcher.Display;
import main.manager.Handler;
import main.manager.MouseManager;
import main.manager.StateManager;
import main.prefs.GamePreferences;
import main.state.MainMenuState;
import main.util.AudioPlayer;
import main.util.DataLoader;
import main.util.ImageLoader;

public class Game implements Runnable{

	private Display display;
	private int width,height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private boolean lastExists;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private StateManager stateManager;
	
	private MouseManager mouseManager;
	
	private Handler handler;
	private GamePreferences preferences;
	private BufferedImage backgroundImage;
	private Color grey_transparent_overlay;
	
	private DataLoader data;
	private ArrayList<String> AnimeList = new ArrayList<String>();
	
	public Game(String title,int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	public void init() {
		
		preferences = new GamePreferences();
		preferences.create();
		
		lastExists = preferences.getLastexists();
		
		backgroundImage=ImageLoader.loadImageIn("/graphics/bg/Anime_BG_2.jpg");
		grey_transparent_overlay = new Color(0.7f, 0.7f, 0.7f, 0.55f);
		
		mouseManager = new MouseManager();
		display = new Display(title,width,height);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		handler = new Handler(this);
		
		
		data = new DataLoader();
		try {
			data.loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		checkAnimeCanBeLoaded();
//		listAnime();
		
		stateManager = new StateManager();
		stateManager.push(new MainMenuState(handler));
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				preferences.writeToXML();
				try {
					running=false;
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		});	
	}
	public void tick() {
		stateManager.tick();
		
//		System.out.println("------------------------");
//		for(int i=0;i<stateManager.stack.size();i++){
//			System.out.println(stateManager.stack.elementAt(i));
//		}
//		System.out.println("------------------------");
		
	}
	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs==null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		//Draw Here
		g.drawImage(backgroundImage, 0, 0,handler.getWidth(),handler.getHeight(), null);
		
		g.setColor(grey_transparent_overlay);
		g.fillRect(0, 0, handler.getWidth(),handler.getHeight());
		
		stateManager.render(g);
		//End drawing
		bs.show();
		g.dispose();
		
	}
	
	@Override
	public void run() {
		init();
		
		int fps = 60;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime)/timePerTick;
			timer+= now - lastTime;
			lastTime = now;
			
			if(delta>=1){
				tick();
				render();
				ticks++;
				delta--;
			}
			if(timer>=1000000000){
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	public synchronized void start(){
		if(running)
			return;
		running =true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public StateManager getStateManager() {
		return stateManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GamePreferences getPreferences(){
		return preferences;
	}
	
	public ArrayList<Anime> getAnimeList(){
		return data.getAnimeNameData();
	}
	public ArrayList<Song> getSongList(){
		return data.getSongData();
	}
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void checkAnimeCanBeLoaded(){
		for(int i=0;i<data.getSongData().size();i++){
			AudioPlayer ap = new AudioPlayer(data.getSongData().get(i).FileName);
			ap.close();
			
			AnimeList.add(data.getSongData().get(i).AnimeName);
			
		}
	}
	public void listAnime(){
		//List for copying and pasting to AnimeData Json
		String lastName="";
		for(int i=0;i<AnimeList.size();i++){
			while(i<AnimeList.size()&&AnimeList.get(i).equals(lastName)){
					AnimeList.remove(i);
			}
			if(i<AnimeList.size()){
				lastName=AnimeList.get(i);
			}
		}
		
		for(int i=0;i<AnimeList.size();i++){
			System.out.println("{\"AnimeName\":\""+AnimeList.get(i)+"\"},");
		}
	}
	public boolean lastExists(){
		return lastExists;
	}
	public void setLastExists(boolean value) {
		this.lastExists = value;
	}
}
