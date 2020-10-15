package com.bridgelabz;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
}
