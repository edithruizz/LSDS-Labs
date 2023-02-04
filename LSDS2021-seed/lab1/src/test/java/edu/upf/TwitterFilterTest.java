package edu.upf;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


import java.io.BufferedReader;
import java.io.FileReader;
import edu.upf.filter.FileLanguageFilter;
//import edu.upf.filter.FilterException;
import edu.upf.uploader.S3Uploader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class TwitterFilterTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    // Place your code here
    public static void main(String[] args) throws Exception {

        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);

        long startTime = System.nanoTime();
        System.out.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);
        for (String inputFile : argsList.subList(3, argsList.size())) {
            System.out.println("Processing: " + inputFile);
            final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
            filter.filterLanguage(language);

        }
        long endTime = System.nanoTime();
        
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        
        
        System.out.println("Took " + (endTime - startTime)/ 1000000000 + " seconds" +
                " to filter tweets in " + language + ".");
        System.out.println("There are " + lines + " tweets in " + language + ".");

        final S3Uploader uploader = new S3Uploader(bucket, "prefix", "default");
        uploader.upload(Arrays.asList(outputFile));
    }

    
}
