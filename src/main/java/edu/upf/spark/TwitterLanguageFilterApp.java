package edu.upf.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import edu.upf.model.SimplifiedTweet;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;

import java.util.Arrays;

public class TwitterLanguageFilterApp {

    public static void main(String[] args){

        String language = args[0];
        String output = args[1];
        String input = args[2];

        System.out.println("Language: " + language + ". Output: " + output + ". Input: " + input);


        //Create a SparkContext to initialize
        SparkConf conf = new SparkConf().setAppName("Twitter Language Filter App");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        
        // Load input
        JavaRDD<String> sentences = sparkContext.textFile(input);

        JavaRDD<SimplifiedTweet> tweets = sentences
            .map(t -> SimplifiedTweet.fromJson(t))
            .filter(w -> w.isPresent())
            .map(g -> g.get())
            .filter(f -> language.equals(f.getLanguage()));

        System.out.println("Total tweets: " + tweets.count());
        tweets.saveAsTextFile(output);
    }

    private static String normalise(String word) {
        return word.trim().toLowerCase();
    }
}
