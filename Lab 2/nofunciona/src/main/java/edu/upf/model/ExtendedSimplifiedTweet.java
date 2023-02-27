package edu.upf.model;

import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import scala.Tuple2;
import java.util.ArrayList;

import java.io.Serializable;

public class ExtendedSimplifiedTweet implements Serializable {
	private final long tweetId;         // the id of the tweet (’id’)
	private final String text;          // the content of the tweet (’text’)
	private final long userId;          // the user id (’user->id’)
	private final String userName;      // the user name (’user’->’name’)
	private final long followersCount;  // the number of followers (’user’->’followers_count’)
	private final String language;      // the language of a tweet (’lang’)
	private final boolean isRetweeted;  // is it a retweet? (the object ’retweeted_status’ exists?)
	private final Long retweetedUserId; // [if retweeted] (’retweeted_status’->’user’->’id’)
	private final Long retweetedTweetId; // [if retweeted] (’retweeted_status’->’id’)
	private final long timestampMs;     // seconds from epoch (’timestamp_ms’)

    // Constructor
	public ExtendedSimplifiedTweet(long tweetId, String text, long userId, String userName, long followersCount,
			String language, boolean isRetweeted, Long retweetedUserId, Long retweetedTweetId, long timestampMs) {

		this.tweetId = tweetId;             // the id of the tweet (’id’)
		this.text = text;                   // the content of the tweet (’text’)
		this.userId = userId;               // the user id (’user->id’)
		this.userName = userName;           // the user name (’user’->’name’)
		this.followersCount = followersCount; // the number of followers (’user’->’followers_count’)
		this.language = language;           // the language of a tweet (’lang’)
		this.isRetweeted = isRetweeted;     // is it a retweet? (the object ’retweeted_status’ exists?)
		this.retweetedUserId = retweetedUserId; // [if retweeted] (’retweeted_status’->’user’->’id’)
		this.retweetedTweetId = retweetedTweetId; // [if retweeted] (’retweeted_status’->’id’)
		this.timestampMs = timestampMs;     // seconds from epoch (’timestamp_ms’)
	}

	/**
	 * Returns a {@link ExtendedSimplifiedTweet} from a JSON String. If parsing
	 * fails, for any reason, return an {@link Optional#empty()}
	 *
	 * @param jsonStr
	 * @return an {@link Optional} of a {@link ExtendedSimplifiedTweet}
	 */
	public static Optional<ExtendedSimplifiedTweet> fromJson(String jsonStr) {
        // IMPLEMENT ME
		
		try {
			//methods getAs.. returns exception if null, if so go catch
			JsonParser parser = new JsonParser();
			JsonElement je = parser.parse(jsonStr);
		  
			JsonObject jo = je.getAsJsonObject();
			Optional<ExtendedSimplifiedTweet> stweet;

			JsonObject userObj = jo.get("user").getAsJsonObject();
		  
			//get all the needed fields
		  	long tweetId = jo.get("id").getAsLong();

		  	long userId = userObj.get("id").getAsLong() ;
			
		  	String text = jo.get("text").getAsString() ;
			
		  	String userName = userObj.get("name").getAsString();
			
		  	long followersCount = userObj.get("followers_count").getAsLong() ;
			
		  	String language = jo.get("lang").getAsString();

		  	boolean isRetweeted;
		  	Long retweetedUserId;
          	Long retweetedTweetId;
		  	
			try {
			  	//if it is a retweet get all the original data and set boolean to true
				JsonObject isRetweetedObj =jo.get("retweeted_status").getAsJsonObject();
				JsonObject retweetedUserObject=isRetweetedObj.get("user").getAsJsonObject();
				retweetedUserId = retweetedUserObject.get("id").getAsLong();
				retweetedTweetId= isRetweetedObj.get("id").getAsLong();
				isRetweeted = true;
			  
		  	} catch(Exception e) {
				//if the tweet is not a retweet set the boolean to false
				isRetweeted=false;
				retweetedUserId = null;
				retweetedTweetId = null;
		  	}
		 
		long timestampMs= jo.get("timestamp_ms").getAsLong();

		//check if a field is null, return empty optional
		stweet = Optional.ofNullable(new ExtendedSimplifiedTweet(tweetId,  text,  userId,  userName,  followersCount,
				language,  isRetweeted,  retweetedUserId,  retweetedTweetId,  timestampMs));
		
		return stweet;
		
		} catch(Exception e) {
			//in case we fail to read the string we return an empty optional
			return Optional.empty();
		}

	}

	// getter for the filter
	public String getLanguage() {
		return language;
	}

	//new getters
	public boolean getIsRetweeted() { 
		return isRetweeted; 
	}

    public long getRetweetedTweetId() { 
    	return retweetedTweetId;
    }

    public long getRetweetedUserId() { 
    	return retweetedUserId;
    }

	public int getRetweetCount() {
		int count = 0;
		if (this.getRetweetedTweetId() == 1){
			count++;
		}
		return count;
	}

    public String getText(){
    	return text;
    }
	
	private static String normalise(String word) {
        return word.trim().replaceAll("\\p{Punct}","").toLowerCase();
    }

	@Override
	public String toString() {
		// Overriding how ExtendedSimplifiedTweet are printed in console or the output file
		// The following line produces valid JSON as output
		return new Gson().toJson(this);
	}
}