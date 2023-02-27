package edu.upf.model;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.Serializable;

public class SimplifiedTweet {

  // All classes use the same instance
  private static JsonParser parser = new JsonParser();

  private final long tweetId;			  // the id of the tweet ('id')
  private final String text;  		  // the content of the tweet ('text')
  private final long userId;			  // the user id ('user->id')
  private final String userName;		// the user name ('user'->'name')
  private final String language;    // the language of a tweet ('lang')
  private final long timestampMs;		// seconduserIds from epoch ('timestamp_ms')

  // Constructor
  public SimplifiedTweet(long tweetId, String text, long userId, String userName, String language, long timestampMs) {
    this.tweetId = tweetId;
    this.text = text;
    this.userId = userId;	
    this.userName = userName;
    this.language = language;
    this.timestampMs = timestampMs;	
  }

  /**
   * 
   * @param o
   * @return boolean indicating if fields are present or not
   */
  public static Boolean checkfields(JsonObject o) {   // check if tweet has all necessary fields

    if (o.has("id") && o.has("text") && o.has("user") && o.has("lang") && o.has("timestamp_ms")) {
      JsonObject o2 = o.getAsJsonObject("user");
      return (o2.has("id") && o2.has("name"));
    }
    else {
      return false;
    }
  }

  /**
   * Returns a {@link SimplifiedTweet} from a JSON String.
   * If parsing fails, for any reason, return an {@link Optional#empty()}
   *
   * @param jsonStr
   * @return an {@link Optional} of a {@link SimplifiedTweet}
   */
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) {
    
    Optional<SimplifiedTweet> st = Optional.empty();
    try{ 
      parser.parse(jsonStr);  // check if object is json

      JsonElement je = parser.parse(jsonStr);
      JsonObject jsonObject = je.getAsJsonObject();
      
      if(checkfields(jsonObject)){      // if all fields are presents
        
        JsonObject jObjects = jsonObject.getAsJsonObject("user");

        long a = jsonObject.get("id").getAsLong();
        String b = jsonObject.get("text").toString();
        long c = jObjects.get("id").getAsLong();    //cambiar
        String d = jObjects.get("name").toString(); // cambair a
        String e = jsonObject.get("lang").toString();
        long f = jsonObject.get("timestamp_ms").getAsLong();

        st = Optional
          .ofNullable(a)
          .flatMap(text -> Optional.ofNullable(b))
          .flatMap(userId -> Optional.ofNullable(c))
          .flatMap(userName -> Optional.ofNullable(d))
          .flatMap(lang -> Optional.ofNullable(e))
          .flatMap(time -> Optional.ofNullable(f))
          .map(g -> new SimplifiedTweet(a, b, c, d, e, f));
      }
    } catch (Exception e) {
      return Optional.empty();    //if parsing fails
    }
    return st;
  }


  @Override
  public String toString() {
    return new Gson().toJson(this);   // transform object this from java to json
    //return "";
  }

/**
 * 
 * @param tweet
 * @return language of the tweet
 */
  public String getLanguage(){
    return language;
  }

  /**
   * 
   * @param tweet
   * @return formated line
   */
  public static String changeFormat(Optional<SimplifiedTweet> tweet){
    SimplifiedTweet something = tweet.get();
    String line =
        "id: "+ something.tweetId +
        "  " + "text: " + something.text +
        "  " + "userId:  " + something.userId +
        "  " + " userName: " +something.userName +
        "  " + "language: " + something.language +
        "  " + "timestampMs: " + something.timestampMs;
    return line;
  }
}
