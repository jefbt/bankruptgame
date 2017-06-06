package com.jefersontomazella.games.bankrupt;

import java.util.ArrayList;
import java.util.Random;

/***
 * Player.java
 * 
 * The player, it's possessions and it's actions.
 * 
 * @author Jeferson Tomazella
 * @version 1.0
 *
 */
public class Player
{
	/**
	 * The player behaviors, adapted from portuguese.
	 * hasty: 	impulsivo;
	 * choosy:	exigente;
	 * careful:	cauteloso;
	 * random:	aleatório.
	 */
	public enum Behavior {
			hasty, choosy, careful, random
	}
	
	private int coins;
	private int position;
	private ArrayList<House> houses;
	private Behavior behavior;
	private boolean lost;
	
	/***
	 * Instantiate the player. It must have a behavior.
	 * 
	 * @param behavior Tells the player what kind of behavior will guide it's actions.
	 */
	public Player(Behavior behavior)
	{
		this.coins 		= Constants.STARTING_COINS;
		this.position	= 0;
		this.houses 	= new ArrayList<House>();
		this.behavior	= behavior;
		this.lost		= false;
	}
	
	/***
	 * Getter for the player coins.
	 * 
	 * @return the player coins.
	 */
	public int getCoins()
	{
		return this.coins;
	}
	
	/**
	 * Getter for the player behavior.
	 * 
	 * @return the behavior.
	 */
	public Behavior getBehavior()
	{
		return this.behavior;
	}
	
	/***
	 * Tells if the player is out of this game.
	 * 
	 * @return true if the player is out of the game, false otherwise.
	 */
	public boolean lost()
	{
		return this.lost;
	}
	
	/***
	 * The player turn. It walks, stops in a house, pay the rent cost if the house has an owner other than itself, chooses to buy or not and tells if the player will continue or will be removed from this game.
	 *  
	 * @param houses Array List of the game houses.
	 * @return true if the player have a negative loan. False otherwise. 
	 */
	public boolean turn(ArrayList<House> houses)
	{
		this.walk(houses.size());
		
		House house = houses.get(this.position-1); 
		
		this.payRentCost(house);
		
		this.action(house);
		
		return this.keepPlaying();
	}
	
	/***
	 * The player receives the house's rent cost in coins, if another player stops by.
	 * 
	 * @param rentCost the house's rent cost.
	 */
	public void receiveRent(int rentCost)
	{
		this.coins += rentCost;
	}
	
	/***
	 * Remove the player from the game and free all its houses.
	 */
	public void lose()
	{
		this.lost = true;
		
		if (this.houses != null && this.houses.size() > 0)
		{
			for (House house : this.houses)
			{
				house.freeHouse();
			}
			this.houses.clear();
			this.houses	= null;
		}
	}
	
	/***
	 * Throw the 6-sided die and walk that number of houses.
	 * 
	 * @param housesCount the total number of houses in the board.
	 */
	private void walk(int housesCount)
	{
		Random 	random	= new Random();
		int		steps	= random.nextInt(Constants.DICE_FACES) + 1;
		
		this.position	+= steps;
		
		if (this.position > housesCount)
		{
			this.position	-= housesCount;
			this.coins		+= Constants.EXTRA_COINS;
		}
	}
	
	/***
	 * Pay the house's rent cost if another player owns it.
	 * 
	 * @param house the house this player stopped by.
	 */
	private void payRentCost(House house)
	{
		if (house.getOwner() != null && house.getOwner().equals(this) == false)
		{
			this.coins -= house.getRentCost();
			house.payRentCost();
		}
	}
	
	/***
	 * Chooses how to buy a house, based on the player behavior.
	 * 
	 * @param house the house this player stopped by.
	 */
	private void action(House house)
	{
		if (house.getOwner() == null)
		{
			switch(this.behavior)
			{
			case hasty:
				this.buyHouse(house);
				break;
			case choosy:
				if (house.getRentCost() > 50)
					this.buyHouse(house);
				break;
			case careful:
				if (this.coins >= house.getBuyCost() + Constants.CAREFUL_SAVING)
					this.buyHouse(house);
				break;
			case random:
				Random rand = new Random();
				if (rand.nextBoolean())
					this.buyHouse(house);
				break;
			}
		}
	}
	
	/***
	 * Buy the house, if the player has enough coins.
	 * 
	 * @param house the house this player stopped by.
	 */
	private void buyHouse(House house)
	{
		if (this.coins >= house.getBuyCost())
		{
			this.coins -= house.getBuyCost();
			house.setOwner(this);
			this.houses.add(house);
		}
	}
	
	/***
	 * Tells if the player will keep playing or will be removed from the game.
	 * 
	 * @return true if the player has zero or more coins, false otherwise.
	 */
	private boolean keepPlaying()
	{
		return this.coins >= 0;
	}
}
