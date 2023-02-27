package edu.upf.spark;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import edu.upf.spark.Bigram;
import edu.upf.model.ExtendedSimplifiedTweet;

public class BiGramsApp {
	public static void main( String[] args ) throws Exception {
		String language = args[0];
	    String output = args[1];
	    String input = args[2];
	    System.out.println("Language: " + language + ". Output file: " + output + ". input file/folder: " + input);


	    //Create a SparkContext to initialize
	    SparkConf conf = new SparkConf().setAppName("Filter App");
	    JavaSparkContext sparkContext = new JavaSparkContext(conf);
	    
        // Load input
	    JavaRDD<String> lines = sparkContext.textFile(input);

	    JavaPairRDD<Integer, Tuple2<String, String>> counts = lines
            .map(t -> ExtendedSimplifiedTweet.fromJson(t))
	        .filter(opt-> opt.isPresent())
	        .map(g ->g.get())
	        .filter(fst->language.equals(fst.getLanguage()))
	        .filter(original -> (original.getIsRetweeted()==false))
	        .map(text->text.getText())
	        
            //split sentences, maybe not very necessary as they are tweets, but still taken into consideration
	        .flatMap(s -> Arrays.asList(s.split("\\.")).iterator())
	        
            //normalize sentences
	        .map(sentences -> normalise(sentences))
	        .flatMap(sentences->Bigram.createBigrams(sentences).iterator())
	        .mapToPair(bigram ->new Tuple2<>(bigram,1))
	        .reduceByKey((a, b) -> a + b)
	        
            //convert values to keys to be able to sort
	        .mapToPair(t -> new Tuple2<>(t._2, t._1));
	        
            //sort descending order
	    JavaPairRDD<Integer, Tuple2<String, String>> out =counts.sortByKey(false);
	    
	    out.saveAsTextFile(output);
	}
	
    private static String normalise(String word) {
        return word.trim().replaceAll("[^\\w\\s]","").toLowerCase();
    }
}