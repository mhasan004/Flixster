package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
//1) Setting up the Using CodePath Async Http Client
//2) parsing JSON data -> moving to a package called "models" in step 3
//3-3.4) make a "models" package in this directory and make a Movie.java class.
    // This class will have a method called makeMovieListFromJsonArray() which will take in a JSONArray and will output a List of "Movie" objects
    // that has the "posterPath", "title", and "overview" of the JSON  text (class also has getters and setters
//4) make the xml files and go make the adapters: make a new 'adapters" package and add "MovieAdapter.java" class inside it.
//5) Back from MovieAdapter.java. Setting up the adapter for the RecycleView (rvMovies)
//6) Updating the data as we scroll down.
        // onCreateViewHolder() Will only display movies (views) on screen till it fills it up (this method takes a lot of time)
        // As we scroll down, more movies wil be use onBindViewHolder() to add to the RV
public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"; //1.0) getting the url (passing in the api key as well into the url"
    public static final String TAG = "MainActivity";                                                // 1.0) Making a tag to easily log data
    List<Movie> movies;                                                                             // 3.5) Make the list of movie object a member variable so recycler view can output it easily

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);                                        // 5.2.1)
        movies = new ArrayList<>();                                                                 // 6.1) if u look at the LinkedList Movies, there is no data there. Data is only in there were we get the url response.

        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);                          // 5.1) Create an adapter (use the MovieAdapter we made)
        rvMovies.setAdapter(movieAdapter);                                                          // 5.2.2) Set the adapter on the RecyclerView
        rvMovies.setLayoutManager(new LinearLayoutManager(this));                            // 5.3 E) Set a Layout Manager on the RecyclerView

        AsyncHttpClient client = new AsyncHttpClient();                                             // 1.1) See How to send Network Data using CodePath Async Http Client: https://github.com/codepath/android_guides/wiki/Using-CodePath-Async-Http-Client
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()                                   // TIP: autocomplete <- type "new JsonHttpResponseHandler()" as a parameter
        {                                                                                           // 1.2 E) We are using a JsonHttpResponseHandler() and not a TextHttpResponseHandler() like in the webpage because we see that this movie API returns json
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");                                                                    // just logging the TAG data so we know it worked!
                JSONObject jsonObject = json.jsonObject;                                            // 2.1) "json" is the entire json response of the site, We are taking that text and turning it into a JSON object so that we can easily extract information from it
                try {
                    JSONArray results = jsonObject.getJSONArray("results");                   // 2.2) String the 'results' key into an array (because the value of the results key is an array). getJSONArray is an unhandled exception. meaning that the results key may not exist, ned to handle it in an catch
                                                                                                                //Log.i(TAG, "Results: " + results.toString());                             //passed the results so im printing what got passed! (shows the entire results key array of movies) NOTE: Log.i not Log.e
                    //movies = Movie.makeMovieListFromJsonArray(results);                           // 3.6 E) 'movies' = List of Movie objects from the JSONArray 'results'
                    movies.addAll(Movie.makeMovieListFromJsonArray(results));                       // 6.2)
                    movieAdapter.notifyDataSetChanged();
                                                                                                                // Log.i(TAG, "Movies: "+ movies.size());
                }
                catch (JSONException e) {
                    Log.e(TAG, "We hit a JSON exception. Maybe there are no 'results' key in the JSON response?", e);   //failed to get the results
                    e.printStackTrace();                                                            // 2.3E)  getJSONArray() is an unhandled exception. meaning that the results key may not exist, need to handle it in an try catch block
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");                                                                    // just logging the TAG data so we know it worked!
            }
        });
    }
}
