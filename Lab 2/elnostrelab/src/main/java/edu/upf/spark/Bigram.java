package edu.upf.spark;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import scala.Tuple2;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Bigram{
	private final List<Tuple2<String,String>> lbigram;

	public Bigram(List<Tuple2<String,String>> lbigram) {
		this.lbigram=lbigram;
	}
		
		 public static List<Tuple2<String,String>> createBigrams(String text) {
				//split sentence into words
		    	String[] words = text.split(" ");

				List<Tuple2<String,String>> ltuples= new ArrayList<>();
				for(int i=0; i<words.length-1; i++){
					if((!(words[i].equals(""))) && (!(words[i + 1].equals(""))))
		            ltuples.add(new Tuple2<>(words[i], words[i + 1]));
		        }	
				
				return ltuples;
		    }
}