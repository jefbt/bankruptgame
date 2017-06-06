package com.jefersontomazella.games.bankrupt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * HousesLoader.java
 * 
 * Loads the houses buy and rent costs from an external file. The values must be in pairs separated by empty spaces.
 * 
 * @author Jeferson Tomazella
 * @version 1.0
 *
 */
public class HousesLoader
{
	/**
	 * Reads the file and converts it into a list.
	 * 
	 * @return the list of houses.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<House> loadHouses() throws FileNotFoundException, IOException
	{
		try(BufferedReader br = new BufferedReader(new FileReader(Constants.COSTS_FILE)))
		{
		    String line = br.readLine();
		    
		    ArrayList<House> houses = new ArrayList<House>();
	
		    while (line != null && line.isEmpty() == false)
		    {
		    	String[] costs	= line.split(" ");
		    	
		    	// The secondItem is used to make sure no empty spaces will be parsed to int.
		    	int secondItem 	= 1;
		    	while(costs[secondItem].isEmpty() || costs[secondItem].equals(" "))
		    		secondItem++;
		    	
		    	int buyCost		= Integer.parseInt(costs[0]);
		    	int rentCost	= Integer.parseInt(costs[secondItem]);
		    	
		    	houses.add(new House(buyCost, rentCost));
		    	
		        line = br.readLine();
		    }
		    
		    if (houses.size() == 0)
		    	return null;
		    
		    return houses;
		}
	}
	
}
