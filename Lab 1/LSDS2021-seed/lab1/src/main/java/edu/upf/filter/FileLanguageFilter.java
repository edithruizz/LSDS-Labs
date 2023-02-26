package edu.upf.filter;
import edu.upf.parser.SimplifiedTweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class FileLanguageFilter {
    final String inputFile;
    final String outputFile;

    // Constructor
    public FileLanguageFilter(String input, String output){
        this.inputFile = input;
        this.outputFile = output;
    }

    public void filterLanguage(String language) throws Exception {

        try (FileReader reader = new FileReader(inputFile);
                BufferedReader bReader = new BufferedReader(reader);
                FileWriter writer = new FileWriter(outputFile, true);
                BufferedWriter bWriter = new BufferedWriter(writer)) {

            String line = bReader.readLine();

            while(line != null) {

                if (line.length() > 0) {
                    Optional<SimplifiedTweet> tweet = SimplifiedTweet.fromJson(line);

                    if (tweet.isPresent()){
                        if ((SimplifiedTweet.getLanguage(tweet)).equals(language)){     // check if tweet is from desired language if it is write
                            String sst = SimplifiedTweet.changeFormat(tweet);
                            bWriter.write(sst);
                            bWriter.newLine();
                        }
                    }
                }
                line = bReader.readLine();
            }
            bReader.close();
            bWriter.close();
        } catch(Exception e) {
            e.getMessage();
        }
    }
}
