package com.jefersontomazella.games.bankrupt;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main.java
 * 
 * The main class for this project, instantiates the statistics and the game objects and run 300 games to show the statistics.
 * 
 * @author Jeferson Tomazella
 * @version 1.0
 *
 */
public class Main
{
	/**
	 * Main method that runs the game logic.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Statistics	statistics	= new Statistics();
		Game 		game		= new Game();
		
		for (int match = 0; match < Constants.TOTAL_MATCHES; match++)
		{
			game.PlayGame(statistics);
		}
		
		statistics.print();
	}
		
}
