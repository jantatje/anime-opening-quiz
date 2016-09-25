package main.launcher;

import main.Game;

public class Launcher {
	public static void main(String[]args){
		Game game = new Game("Anime Opening Quiz",2270/2,1800/2);
		game.start();
	}
}
