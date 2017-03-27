import java.util.*;
import java.lang.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MovieEngine{
  private JSONArray movieList;
  private JSONObject inputMovie;
  private JSONArray similarMovies;

  // Constructor takes the list of all movies.
  public MovieEngine(JSONArray movieList){
    this.movieList = movieList;
  }

  // Generates a Json Array of top 10 similar movies.
  public JSONArray top10SimilarMovies(JSONObject inputMovie){
    JSONArray jsonArray = allSimilarMovies(inputMovie);
    JSONArray top10Movies = new JSONArray();
    List<JSONObject> jsonList = new ArrayList<JSONObject>();
    Iterator jsonArrayIterator = jsonArray.iterator();

    while(jsonArrayIterator.hasNext()){
      jsonList.add((JSONObject) jsonArrayIterator.next());
    }

    Collections.sort( jsonList, new Comparator<JSONObject>() {
      public int compare(JSONObject a, JSONObject b) {
        Integer valA = new Integer((int) a.get("score"));
        Integer valB = new Integer((int) b.get("score"));
        return -valA.compareTo(valB);
      }
    });

    for (int i = 0; i < 10; i++) {
      top10Movies.add((JSONObject) jsonList.get(i));
    }

    return top10Movies;
  }

  // Generates a Json Array of all movies with their similarity scores wrt input movie.
  private JSONArray allSimilarMovies(JSONObject inputMovie){
    this.inputMovie = inputMovie;
    this.similarMovies = new JSONArray();

    Iterator movieListIterator = movieList.iterator();

    while(movieListIterator.hasNext()){
      JSONObject anotherMovie = (JSONObject) movieListIterator.next();
      int score = similarityScore(anotherMovie);
      anotherMovie.put("score", score);
      similarMovies.add(anotherMovie);
    }

    return similarMovies;
  }

  // Given two movies, calculates their similarity scores.
  private int similarityScore(JSONObject anotherMovie){
    int score = 0;
    if(anotherMovie.get("movie_id") != inputMovie.get("movie_id")){
      score += actorScore(anotherMovie);
      score += genreScore(anotherMovie);
      score += directorScore(anotherMovie);
    }

    return score;
  }

  // Given two movies, calculates how similar two movies are, with respect to actors.
  private int actorScore(JSONObject anotherMovie){
    int score = 0;
    JSONArray inputMovieActors = (JSONArray) inputMovie.get("stars");
    JSONArray anotherMovieActors = (JSONArray) anotherMovie.get("stars");

    Iterator<String> inputMovieIterator = inputMovieActors.iterator();

    while(inputMovieIterator.hasNext()){
      String inputMovieActor = (String) inputMovieIterator.next();
      Iterator<String> anotherMovieIterator = anotherMovieActors.iterator();
      while(anotherMovieIterator.hasNext()){
        String anotherMovieActor = (String) anotherMovieIterator.next();
        if(inputMovieActor.equals(anotherMovieActor)){
          score += 1;
        }
      }
    }

    return score;
  }

  // Given two movies, calculates how similar two movies are, with respect to genres.
  private int genreScore(JSONObject anotherMovie){
    int score = 0;
    JSONArray inputMovieGenres = (JSONArray) inputMovie.get("genre");
    JSONArray anotherMovieGenres = (JSONArray) anotherMovie.get("genre");

    Iterator<String> inputMovieIterator = inputMovieGenres.iterator();

    while(inputMovieIterator.hasNext()){
      String inputMovieGenre = inputMovieIterator.next();
      Iterator<String> anotherMovieIterator = anotherMovieGenres.iterator();
      while(anotherMovieIterator.hasNext()){
        String anotherMovieGenre = anotherMovieIterator.next();
        if(inputMovieGenre.equals(anotherMovieGenre)){
          score += 1;
        }
      }
    }

    return score;
  }

  // Given two movies, calculates how similar two movies are, with respect to directors.
  private int directorScore(JSONObject anotherMovie){
    int score = 0;
    String inputMovieDirector = (String) inputMovie.get("director");
    String anotherMovieDirector = (String) anotherMovie.get("director");

    if(inputMovieDirector.equals(anotherMovieDirector)){
      score += 1;
    }

    return score;
  }

}