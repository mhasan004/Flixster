package com.example.flixster.models;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

// Diving the processes. what Main will go and what movies will do. dividing the logics to be clearer
//3) my makeMovieListFromJsonArray(): make a List of movie objects from a given JSONArray input
public class Movie {
    String posterPath;                                                      //3.0) These are the fields i care about from that JSON object that the website api gives us
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException{               //3.1) The constructor will make a movie object. If any of these fails, the constructor will throw the JSON Exception
        posterPath = jsonObject.getString("poster_path");             //3.1) These will have exceptions, instead of putting throw catches, we will add a "throws JSONException" in the contructor def
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }
                                                                            //3.2-3.3) Construct and return a List of 'Movie' object for each item in the array (will pass the'results' JSONArray from Main)
    public static List<Movie> makeMovieListFromJsonArray(JSONArray movieJsonArray) throws JSONException
    {                                                                       //3.2) The 'makeMovieListFromJsonArray()' method will return a list of movies, input: a JSONArray
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<movieJsonArray.length(); ++i){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));         //3.3) will add a movie object to each position in the list
        }
        return movies;
    }

    //3.4) Getters
    public String getPosterPath() {                                             //3.4.1) ISSUE: From the JSON of the site u see that posterPath is only the relative URL (not full URL). See Hints tab of Week1
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);          //**Hardcoding the path. Should be using the API to find the correct size for attribute: Size of posters are w342, then we are just appending the path to it
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}

// 3.5) Go to main to use the makeMovieListFromJsonArray() to return a List of movie objects in 3.4