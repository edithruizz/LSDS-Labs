package edu.upf.filter;
import edu.upf.parser.SimplifiedTweet;

import java.io.*;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class FileLanguageFilter {
    final String inputFile;
    final String outputFile;

    public FileLanguageFilter(String inputFile, String outputFile){

        this.inputFile = inputFile;
        this.outputFile = outputFile;

    }

	public void filterLanguage(String language) throws IOException{
		BufferedReader bufferedReader= null;
		BufferedWriter bufferedWriter= null;
		
		try {
			//Creates a new FileReader, given the File to read from.
 
		FileReader reader = new FileReader(inputFile);

		 bufferedReader = new BufferedReader(reader);
		 FileWriter writer = new FileWriter(outputFile);
		bufferedWriter = new BufferedWriter(writer);

		 String line;
		 while((line= bufferedReader.readLine()) != null){
			 Optional<SimplifiedTweet> t = SimplifiedTweet.fromJson(line);
			 if(t.isPresent()) {
				 String lang = t.get().getLanguage();
				 if(language.equals(lang)) {
						bufferedWriter.write(t.get().toString()); // Write one line of content with override given method
						bufferedWriter.newLine(); //needed so next line is on a new line
					 
				 }
			 }
		 }
			
			
		} catch(IOException ex) {
		 ex.printStackTrace();
		 throw ex;
		} finally {
		 // Close resources
			if (bufferedReader != null) {
				 bufferedReader.close();// Close buffered reader and enclosed reader

			}
			if (bufferedWriter != null) {
				 bufferedWriter.close(); // Close buffered writer and enclosed writer

			}
			
		}
		}
}
