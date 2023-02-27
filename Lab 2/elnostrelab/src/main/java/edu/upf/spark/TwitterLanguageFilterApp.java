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
    System.out.println("Language: " + language + ". Output file: " + output + ". input file/folder: " + input);


  //Create a SparkContext to initialize
    SparkConf conf = new SparkConf().setAppName("Filter App");
    JavaSparkContext sparkContext = new JavaSparkContext(conf);
    // Load input
    JavaRDD<String> lines = sparkContext.textFile(input);

    JavaRDD<SimplifiedTweet> stweet = lines
        .map(t -> SimplifiedTweet.fromJson(t))
        .filter(opt-> opt.isPresent())
        .map(g ->g.get())
        .filter(fst->language.equals(fst.getLanguage()));
    

    stweet.saveAsTextFile(output);

	}
}
