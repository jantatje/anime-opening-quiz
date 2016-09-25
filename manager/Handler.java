package main.manager;

import java.util.ArrayList;

import main.Game;
import main.data.Anime;
import main.data.Song;
import main.prefs.GamePreferences;


public class Handler {

	private Game game;
	
	public Handler(Game game){
		this.game = game;
	}
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	public StateManager getStateManager(){
		return game.getStateManager();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public GamePreferences getPrefs(){
		return game.getPreferences();
	}
	public ArrayList<Anime> getAnimeList(){
		return game.getAnimeList();
	}
	public ArrayList<Song> getSongList(){
		return game.getSongList();
	}
	public boolean lastExists(){
		return game.lastExists();
	}
	public void setLastExists(boolean value){
		getPrefs().setLastExists(value);
		game.setLastExists(value);
	}
}
