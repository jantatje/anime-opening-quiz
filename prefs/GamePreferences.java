package main.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import main.data.Anime;
import main.data.Song;

public class GamePreferences {

	private static final String volume = "volume";
	private static final String loopSong = "loopSong";
	private static final String currStreak = "currStreak";
	private static final String bestStreak = "bestStreak";
	private static final String difficulty = "difficulty";
	
	private static final String lastExists = "lastExists";
	
	private static final String lastSongAnimeName = "lastSongAnimeName";
	private static final String lastSongFileName = "lastSongFileName";
	private static final String lastSongInterpret = "lastSongInterpret";
	private static final String lastSongTitle = "lastSongTitle";
	
	private static final String Anime1 = "anime1";
	private static final String Anime2 = "anime2";
	private static final String Anime3 = "anime3";
	
	private static Properties userProp;
	
	public void create(){
		userProp = new Properties();
		loadFromXML();
	}
	public void writeToXML() {
		try {
			FileOutputStream FOS = new FileOutputStream(new File(GamePreferences.class.getResource("/data/prefs.xml").toURI()));
			userProp.storeToXML(FOS, "Anime Opening Quiz Preferences");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromXML(){
		try {
			FileInputStream FIS = new FileInputStream(new File(GamePreferences.class.getResource("/data/prefs.xml").toURI()));
			userProp.loadFromXML(FIS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public void setVolume(float value){
		userProp.setProperty(volume, ""+value);
	}
	public void setLooping(boolean value){
		userProp.setProperty(loopSong, ""+value);
	}
	public void setCurrStreak(int value){
		userProp.setProperty(currStreak, ""+value);
	}
	public void setBestStrak(int value){
		userProp.setProperty(bestStreak, ""+value);
	}
	public void setDifficulty(int value){
		userProp.setProperty(difficulty, ""+value);
	}
	public void setLastExists(boolean value){
		userProp.setProperty(lastExists, String.valueOf(value));
	}
	public void setLastSong(Song value){
		userProp.setProperty(lastSongAnimeName, value.AnimeName);
		userProp.setProperty(lastSongFileName, value.FileName);
		userProp.setProperty(lastSongInterpret, value.Interpret);
		userProp.setProperty(lastSongTitle, value.Title);
	}
	public void setAnime1(Anime value) {
		userProp.setProperty(Anime1, value.AnimeName);
	}
	public void setAnime2(Anime value) {
		userProp.setProperty(Anime2, value.AnimeName);
	}
	public void setAnime3(Anime value) {
		userProp.setProperty(Anime3, value.AnimeName);
	}
	
	
	public boolean getLastexists() {
		return (boolean) Boolean.parseBoolean(userProp.getProperty(lastExists,"false"));
	}
	public Song getLastsong() {
		Song song = new Song();
		song.AnimeName = userProp.getProperty(lastSongAnimeName);
		song.FileName = userProp.getProperty(lastSongFileName);
		song.Interpret = userProp.getProperty(lastSongInterpret);
		song.Title = userProp.getProperty(lastSongTitle);
		return song;
	}
	public Anime getAnime1() {
		Anime a = new Anime();
		a.AnimeName = userProp.getProperty(Anime1);
		return a;
	}
	public Anime getAnime2() {
		Anime a = new Anime();
		a.AnimeName = userProp.getProperty(Anime2);
		return a;
	}
	public Anime getAnime3() {
		Anime a = new Anime();
		a.AnimeName = userProp.getProperty(Anime3);
		return a;
	}
	
	
	public float getVolume() {
		return (float) Float.parseFloat(userProp.getProperty(volume, ""+0.5f));
	}
	public boolean getLooping() {
		return (boolean) Boolean.getBoolean(userProp.getProperty(loopSong));
	}
	public int getCurrStreak() {
		return (int) Integer.parseInt(userProp.getProperty(currStreak, ""+0));
	}
	public int getBestStrak() {
		return (int) Integer.parseInt(userProp.getProperty(bestStreak, ""+0));
	}
	public int getDifficulty() {
		return (int) Integer.parseInt(userProp.getProperty(difficulty, ""+1));
	}
}
