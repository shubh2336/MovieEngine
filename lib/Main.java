import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
  public static void main(String[] args){
    JSONArray movies = new JSONArray();

    // Parse the input file.
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader("../data/input.json"));
      movies = (JSONArray) obj;
    } catch (Exception e) {
      e.printStackTrace();
    }

    Iterator iter = movies.iterator();
    JSONObject listOfAllSimilarMovies = new JSONObject();
    MovieEngine engine = new MovieEngine(movies);

    // Calculate similarities of all movies.
    while(iter.hasNext()){
      JSONObject inputMovie =  (JSONObject) iter.next();
      JSONArray similarMovies = engine.top10SimilarMovies(inputMovie);
      Iterator similarMovieIterator = similarMovies.iterator();
      List<String> top10Movies = new ArrayList<String>();;

      while(similarMovieIterator.hasNext()){
        JSONObject similarMovie = (JSONObject) similarMovieIterator.next();
        top10Movies.add((String)similarMovie.get("title"));
      }

      listOfAllSimilarMovies.put(inputMovie.get("title"), top10Movies);
    }

    // Spit top 10 similar movies of each input movie in an output file.
    try{
      FileWriter file = new FileWriter("../data/output.json");
      file.write(listOfAllSimilarMovies.toJSONString());
      file.flush();
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}