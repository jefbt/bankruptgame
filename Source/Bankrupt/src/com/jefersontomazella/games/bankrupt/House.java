package com.jefersontomazella.games.bankrupt;

/***
 * House.java
 * 
 * The game's houses. Has an owner, a buy and a rent costs.
 * 
 * @author Jeferson Tomazella
 * @version 1.0
 * 
 */
public class House
{
	private int buyCost;
	private int rentCost;
	private Player owner;
	
	/***
	 * A house must have a buy cost and a rent cost. Instantiate a new house from the houses file.
	 * 
	 * @param buyCost	how many coins the house costs to buy.
	 * @param rentCost	how many coins a player must pay when it stops in this house.
	 */
	public House(int buyCost, int rentCost)
	{
		this.buyCost	= buyCost;
		this.rentCost	= rentCost;
		this.owner		= null;
	}
	
	/***
	 * Sets a new owner to the hose.
	 * 
	 * @param newOwner	the new owner of this house
	 */
	public void setOwner(Player newOwner)
	{
		this.owner = newOwner;
	}
	
	/***
	 * If this house has an owner, the player pay it the rent cost when another player stops by.
	 */
	public void payRentCost()
	{
		if (this.owner != null)
		{
			this.owner.receiveRent(this.rentCost);
		}
	}
	
	/***
	 * Getter of the buy cost.
	 * 
	 * @return the buy cost.
	 */
	public int getBuyCost()
	{
		return this.buyCost;
	}
	
	/***
	 * Getter of the rent cost.
	 * 
	 * @return the rent cost.
	 */
	public int getRentCost()
	{
		return this.rentCost;
	}
	
	/***
	 * Getter of the owner.
	 * 
	 * @return the owner player of this house.
	 */
	public Player getOwner()
	{
		return this.owner;
	}
	
	/***
	 * When a player loses, the house loses it's owner.
	 */
	public void freeHouse()
	{
		this.setOwner(null);
	}
}
