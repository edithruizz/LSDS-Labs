package edu.upf.spark;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import edu.upf.model.SimplifiedTweet;

public class TwitterLanguageFilterApp {
	public static void main( String[] args ) throws Exception {
	  String language = args[0];
    String output = args[1];
    String input = args[2];
    
    System.out.println("Language: " + language + ". Output: " + output + ". Input: " + input);

    // Create a SparkContext to initialize
    SparkConf conf = new SparkConf().setAppName("TwitterLanguageFilterApp");
    JavaSparkContext sparkContext = new JavaSparkContext(conf);
    
    // Load input
    JavaRDD<String> lines = sparkContext.textFile(input);

    JavaRDD<SimplifiedTweet> tweets = lines
        .map(t -> SimplifiedTweet.fromJson(t))
        .filter(o -> o.isPresent())
        .map(g -> g.get())
        .filter(f -> language.equals(f.getLanguage()));
    
    System.out.println("Total tweets: " + tweets.count());
    tweets.saveAsTextFile(output);
	}

  private static String normalise(String word) {
    return word.trim().toLowerCase();
  }

}
