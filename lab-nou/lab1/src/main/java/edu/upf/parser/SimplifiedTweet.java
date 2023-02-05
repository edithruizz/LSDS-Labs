package edu.upf.parser;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.Serializable;
public class SimplifiedTweet {

  // All classes use the same instance
  private static JsonParser parser = new JsonParser();
  private final long tweetId;			    // the id of the tweet ('id')
  private final String text;  		    // the content of the tweet ('text')
  private final long userId;			    // the user id ('user->id')
  private final String userName;		  // the user name ('user'->'name')
  private final String language;      // the language of a tweet ('lang')
  private final long timestampMs;		  // seconduserIds from epoch ('timestamp_ms')

  public SimplifiedTweet(long tweetId, String text, long userId, String userName, String language, long timestampMs) {

    // PLACE YOUR CODE HERE!
    this.tweetId = tweetId;
    this.text = text;
    this.userId = userId;
    this.userName = userName;
    this.language = language;
    this.timestampMs = timestampMs;

  }

  

  /**
   * Returns a {@link SimplifiedTweet} from a JSON String.
   * If parsing fails, for any reason, return an {@link Optional#empty()}
   *
   * @param jsonStr
   * @return an {@link Optional} of a {@link SimplifiedTweet}
   */
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) {

	  try {
      //methods getAs.. returns exception if null, if so go catch
      JsonElement je = parser.parse(jsonStr);
      JsonObject jo = je.getAsJsonObject();
      Optional<SimplifiedTweet> tweet;

      JsonObject userObj = jo.get("user")
          .getAsJsonObject();
      
      long tweetId = jo.get("id")
      .getAsLong();
      
      String text =jo.get("text")
          .getAsString() ;
      
      long userId =userObj.get("id")
              .getAsLong() ;
      
      String userName= userObj.get("name")
              .getAsString();
      String language = jo.get("lang")
          .getAsString(); ;
      
      
    long timestampMs= jo.get("timestamp_ms").getAsLong();

      //check if a field is null, return empty optional
    tweet = Optional.ofNullable(new SimplifiedTweet(tweetId,text, userId, userName, language, timestampMs));
	  return tweet;
	  }catch(Exception e) {//in case we fail to read the string we return an empty optional
	      return Optional.empty();
	  }
  }

  //getter for the filter
  public String getLanguage() {
	  return language;
  }

  @Override
  public String toString() {
    //crec q no cal fer res mes
    return new Gson().toJson(this); //passa el objecte sencer (this) de java a json
  }

  

  
}
