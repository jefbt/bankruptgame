package com.jefersontomazella.games.bankrupt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/***
 * Game.java
 * 
 * Keeps track of all the houses, instantiate the players and runs the game.
 * 
 * @author Jeferson Tomazella
 * @version 1.0 *
 */
public class Game
{
	private ArrayList<Player>	players;
	private ArrayList<House>	houses;
	
	/***
	 * Instantiate the player list and load the houses.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Game() throws FileNotFoundException, IOException
	{
		this.players	= new ArrayList<Player>();
		this.houses		= HousesLoader.loadHouses();
	}
	
	/***
	 * Play an entire game session. Instantiate the four player, each of one behavior, select a random starting player, prepare the houses and runs all the game's rounds. Stop if someone is the only player with money or if the game reaches 1000 rounds.
	 * 
	 * @param statistics the statistics object, to keep track of the game data.
	 */
	public void PlayGame(Statistics statistics)
	{
		Player 				winner			= null;
		int 				rounds			= 0;
		boolean 			timeoutGame		= false;
		
		// remove all the house's owners from the last game.
		for (House house : this.houses)
		{
			house.freeHouse();
		}
		
		// Instantiate and select the player order.
		this.players.clear();
		
		this.players.add(new Player(Player.Behavior.hasty));
		this.players.add(new Player(Player.Behavior.choosy));
		this.players.add(new Player(Player.Behavior.careful));
		this.players.add(new Player(Player.Behavior.random));
			
		Collections.shuffle(this.players);
		
		int playersLost	= 0;
		
		// Runs the game rounds until there's only one player with coins or the game lasts 1000 rounds.
		while (rounds < Constants.TIMEOUT && winner == null)
		{
			rounds++;
			
			// Each player turn
			for (Player player : this.players)
			{
				// If the player is in the game...
				if (player.lost() == false)
				{
					//... run it's turn and see if it keep playing or will be removed.
					if (player.turn(houses) == false)
					{
						player.lose();
						playersLost++;
					}
				}
				
				// if there is only one player remaining, find the winner and finish the loop.
				if (playersLost == this.players.size()-1)
				{
					for (Player p : this.players)
					{
						if (p.lost() == false) winner = p;
					}
					break;
				}
			}
		}
		
		// If there's no winner yet, the game has reached the timeout, 1000 rounds. Find the player with more coins, untie with the game order.
		if (winner == null)
		{
			timeoutGame = true;
			
			winner = this.players.get(0);
			
			for (Player player : this.players)
			{
				if (player.equals(winner))
					continue;
				
				if  (player.getCoins() > winner.getCoins() ||
						(player.getCoins() == winner.getCoins() &&
						this.players.indexOf(player) < this.players.indexOf(winner))
					)
				{
					winner = player;
				}
			}
		}
		
		// Adds everything to the statistics.
		statistics.add(timeoutGame, winner.getBehavior(), rounds);
	}
}
