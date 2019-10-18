package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;
//1) Setting up the Using CodePath Async Http Client
//2) parsing JSON data
public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"; //1.0) getting the url (passing in the api key as well into the url"
    public static final String TAG = "MainActivity";                            //1.0) Making a tag to easily log data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();                         //1.1) See How to send Network Data using CodePath Async Http Client: https://github.com/codepath/android_guides/wiki/Using-CodePath-Async-Http-Client
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()               // TIP: autocomplete <- type "new JsonHttpResponseHandler()" as a parameter
        {                                                                       //1.2) We are using a JsonHttpResponseHandler() and not a TextHttpResponseHandler() like in the webpage because we see that this movie API returns json
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");                                            // just logging the TAG data so we know it worked!
                JSONObject jsonObject = json.jsonObject;                                 // 1.3) We know the returned result is an object
                try {
                    JSONArray results = jsonObject.getJSONArray("results");        // 1.4) String the 'results' key into an array (because the value of the results key is an array). getJSONArray is an unhandled exception. meaning that the results key may not exist, ned to handle it in an catch
                    Log.i(TAG, "Results" + results.toString());                             //passed the results so im printing what got passed! NOTE: Log.i not Log.e
                } catch (JSONException e) {
                    Log.e(TAG, "We hit a JSON exception. Maybe there are no 'results' key in the JSON response?", e);   //failed to get the results
                    e.printStackTrace();                                                 // 1.4)  getJSONArray() is an unhandled exception. meaning that the results key may not exist, need to handle it in an try catch block
                }

            }


            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");                                            // just logging the TAG data so we know it worked!
            }
        });

    }
}
