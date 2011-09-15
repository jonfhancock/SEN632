package com.apps4you.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.apps4you.shared.Origins;
import com.apps4you.shared.Warrior;
import com.apps4you.shared.WarriorFactory;

public class Utils {

	public static Warrior readFileToCreateCombatant(File file) {
		Warrior combatant = null;
		try {
			FileInputStream fStream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fStream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  combatant = WarriorFactory.fromJSON(strLine);
			  }
			  //Close the input stream
			  in.close();
			  fStream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Setup reading of the serialized wdat file here
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		System.out.println(combatant.toString());
		return combatant;
	}
	public static void saveWarriorToFile(Warrior warrior, File file){
		try {
			FileWriter fStream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fStream);
			out.write(WarriorFactory.toJSON(warrior));
//			System.out.println(WarriorFactory.toJSON(warrior));
			out.close();
			fStream.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}