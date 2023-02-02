package edu.upf.parser;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

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

  public static Boolean checkfields(JsonObject o) {

    if (o.has("id") && o.has("text") && o.has("user") && o.has("lang") && o.has("timestamp_ms")) {
        JsonObject o2 = o.getAsJsonObject("user");
        return (o2.has("id") && o2.has("name"));
    } else {
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
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) { //passa una string
    // PLACE YOUR CODE HERE!
    Optional<SimplifiedTweet> st = Optional.empty();
    try{ 
      parser.parse(jsonStr); //primer detectar si l'objecte es Json
      JsonElement je = parser.parse(jsonStr);
      JsonObject jsonObject = je.getAsJsonObject();
      if(checkfields(jsonObject)){ //te tots els camps: tweetId, text, userId, userName, language, timestampMs
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
    }catch (Exception e) {
      return Optional.empty();    //if parsing fails
    }
    return st;
  }

  @Override
  public String toString() {
    //crec q no cal fer res mes
    return new Gson().toJson(this); //passa el objecte sencer (this) de java a json
    //return "";
  }

  /**
   * 
   * @param tweet
   * @return
   */
  public static String getLanguage(Optional<SimplifiedTweet> tweet){
    String lang = tweet.get().language;
    lang = lang.substring(1, lang.length()-1);
    return lang;
  }

  /**
   * 
   * @param tweet
   * @return
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
