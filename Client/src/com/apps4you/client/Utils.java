package com.apps4you.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.apps4you.shared.Warrior;
import com.apps4you.shared.WarriorFactory;

/**
 * Utility class to add in file writing and reading
 * @author Jon Hancock
 *
 */
public class Utils {

	/**
	 * Reads the File that creates a new Warrior
	 * @param file
	 * @return
	 */
	public static Warrior readFileToCreateWarrior(File file) {
		Warrior combatant = null;
		try 
		{
			//Set the streams properly first
			FileInputStream fStream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)
			{
			  //Actually write the file	
			  combatant = WarriorFactory.fromJSON(strLine);
			}
		  //Close the input stream
		  in.close();
		  fStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// Setup reading of the serialized wdat file here
		catch (IOException e) {
			e.printStackTrace();
		}

		if (Consts.LOGGING) {
		  System.out.println(combatant.toString());}
		return combatant;
	}
	/**
	 * The writting out of the warrior 
	 * @param warrior
	 * @param file
	 */
	public static void saveWarriorToFile(Warrior warrior, File file){
		try {
			//Setup the streams
			FileWriter fStream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fStream);
			
			//Actually write the file
			out.write(WarriorFactory.toJSON(warrior));
			
			//Cleanup
			out.close();
			fStream.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
