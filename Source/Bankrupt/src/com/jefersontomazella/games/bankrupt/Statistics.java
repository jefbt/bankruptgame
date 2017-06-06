package com.jefersontomazella.games.bankrupt;

import java.util.ArrayList;

/***
 * Statistics.java
 * 
 * Keeps track of the game's statistics to show the results after running.
 * 
 * @author Jeferson Tomazella
 * @version 1.0
 *
 */
public class Statistics
{
	private int timeoutMatches;
	private int hastyWins;
	private int choosyWins;
	private int carefulWins;
	private int randomWins;
	private int totalRounds;
	private int winnerCount;
	
	/***
	 * Constructor initialize all the attributes.
	 */
	public Statistics()
	{
		this.timeoutMatches	= 0;
		this.hastyWins		= 0;
		this.choosyWins		= 0;
		this.carefulWins	= 0;
		this.randomWins		= 0;
		this.totalRounds	= 0;
		this.winnerCount	= 0;
	}
	
	/***
	 * Add a new statistic, called after each game.
	 * 
	 * @param timeout			tells if the game has ended by timeout.
	 * @param winnerBehavior	the behavior of the winning player.
	 * @param rounds			how many rounds lasted the game.
	 */
	public void add(boolean timeout, Player.Behavior winnerBehavior, int rounds)
	{
		this.totalRounds += rounds;
		
		if (timeout)	timeoutRound();
		
		switch(winnerBehavior)
		{
		case hasty: 	this.hastyWin();	break;
		case choosy: 	this.choosyWin(); 	break;
		case careful: 	this.carefulWin(); 	break;
		case random: 	this.randomWin(); 	break;
		}
	}
	
	/***
	 * Increase the amount of timeout matches.
	 */
	private void timeoutRound()
	{
		this.timeoutMatches++;
	}
	
	/***
	 * The hasty behavior won last match.
	 */
	private void hastyWin()
	{
		this.hastyWins++;
		
		if (this.hastyWins >= this.choosyWins &&
			this.hastyWins >= this.carefulWins &&
			this.hastyWins >= this.randomWins)
		{
			this.winnerCount	= this.hastyWins;
		}
	}
	
	/***
	 * The choosy behavior won last match.
	 */
	private void choosyWin()
	{
		this.choosyWins++;
		
		if (this.choosyWins >= this.hastyWins &&
			this.choosyWins >= this.carefulWins &&
			this.choosyWins >= this.randomWins)
		{
			this.winnerCount	= this.choosyWins;
		}
	}
	
	/***
	 * The careful behavior won last match.
	 */
	private void carefulWin()
	{
		this.carefulWins++;
		
		if (this.carefulWins >= this.choosyWins &&
			this.carefulWins >= this.hastyWins &&
			this.carefulWins >= this.randomWins)
		{
			this.winnerCount	= this.carefulWins;
		}
	}
	
	/***
	 * The random behavior won last match.
	 */
	private void randomWin()
	{
		this.randomWins++;
		
		if (this.randomWins >= this.choosyWins &&
			this.randomWins >= this.carefulWins &&
			this.randomWins >= this.hastyWins)
		{
			this.winnerCount	= this.randomWins;
		}
	}
	
	/***
	 * Prints all the statistics data to the console.
	 */
	public void print()
	{
		// Calculates the percentage of each behavior win
		int 	averageRoundsPerMatch	= this.totalRounds / Constants.TOTAL_MATCHES;
		
		float 	floatTotalMatches		= (float)Constants.TOTAL_MATCHES;
		float 	hastyWinsPercent		= ((float)this.hastyWins) 	* 100f / floatTotalMatches;
		float 	choosyWinsPercent		= ((float)this.choosyWins) 	* 100f / floatTotalMatches;
		float 	carefulWinsPercent		= ((float)this.carefulWins) * 100f / floatTotalMatches;
		float 	randomWinsPercent		= ((float)this.randomWins) 	* 100f / floatTotalMatches;
		
		// Creates a list of the winners behaviors, if there's a tie
		ArrayList<Player.Behavior> 	winnersList		= new ArrayList<Player.Behavior>();
				
		if (this.hastyWins == winnerCount) 		winnersList.add(Player.Behavior.hasty);
		if (this.choosyWins == winnerCount) 	winnersList.add(Player.Behavior.choosy);
		if (this.carefulWins == winnerCount) 	winnersList.add(Player.Behavior.careful);
		if (this.randomWins == winnerCount) 	winnersList.add(Player.Behavior.random);
		
		// Shows the statistics
		System.out.println("Total matches:            "+Constants.TOTAL_MATCHES);
		System.out.println("Total timeout matches:    "+this.timeoutMatches);
		System.out.println("Average rounds per match: "+averageRoundsPerMatch);
		System.out.println("Hasty behavior wins:      "+hastyWinsPercent+"%");
		System.out.println("Choosy behavior wins:     "+choosyWinsPercent+"%");
		System.out.println("Careful behavior wins:    "+carefulWinsPercent+"%");
		System.out.println("Random behavior wins:     "+randomWinsPercent+"%");
		
		// Shows the winner behavior (or each winner, if there's a tie)
		if (winnersList.size() == 4)
		{
			System.out.println("All behaviors have a tie for the behavior with most wins.");
		}
		else if (winnersList.size() > 1)
		{
			System.out.print("The behaviors ");
			for (int i = 0; i < winnersList.size() - 2; i++)
			{
				System.out.print(winnersList.get(i).toString()+", ");
			}
			System.out.print(winnersList.get(winnersList.size()-2).toString()+" and ");
			System.out.println(winnersList.get(winnersList.size()-1).toString()+" are tied for the behavior with most wins.");
		} 
		else if (winnersList.size() == 1)
		{
			System.out.println("The behavior with most wins is the "+winnersList.get(0).toString()+" behavior.");
		}
		
	}
}
