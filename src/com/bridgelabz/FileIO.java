package com.bridgelabz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class FileIO {

	public static void writeData(String filePath) {
		
		ArrayList<Info> friends = null;
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);

			// adding header to csv
			String[] header = { "firstName", "lastName", "address", "city", "state", "zipCode", "phoneNo", "email" };
			writer.writeNext(header);

			// add data to csv
			for (Info c : friends) {
				String[] dataStr = c.pushDataCSV();
				writer.writeNext(dataStr);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void readData(String file) 
	{ 
	    try { 
	  
	        FileReader filereader = new FileReader(file); 
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	        while ((nextRecord = csvReader.readNext()) != null) { 
	            for (String cell : nextRecord) { 
	                System.out.print(cell + "\t"); 
	            } 
	            System.out.println(); 
	        } 
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	} 
/*	package reading_file;

	import java.io.*;
	import java.util.ArrayList;

;*/

	public static void readJSONFile(String fileNameJson){
		ArrayList<JSONObject> json=new ArrayList<JSONObject>();
	    JSONObject obj;

	    // This will reference one line at a time
	    String line = null;

	    try {
	        // FileReader reads text files in the default encoding.
	        FileReader fileReader = new FileReader(fileNameJson);

	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        while((line = bufferedReader.readLine()) != null) {
	            obj = (JSONObject) new JSONParser().parse(line);
	            json.add(obj);
	            System.out.println((String)obj.get("fName")+":"+(String)obj.get("lName")+":"+(String)obj.get("address")+":"+(String)obj.get("city")+":"+
	            					(String)obj.get("state")+":"+(String)obj.get("zip")+":"+(String)obj.get("phone")+":"+(String)obj.get("email"));
	        }
	        
	        bufferedReader.close();         
	    }
	    catch(Exception ex) {
	    	 ex.printStackTrace(); 
	    }
	   
	}
	
public void writeDatatoJson(ArrayList<Info> friends, String JSON_FILE_WRITE) {
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(JSON_FILE_WRITE));
			new Gson().toJson(friends,writer);
			writer.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
			
		
}
}
}

