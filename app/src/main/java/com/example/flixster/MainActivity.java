package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.codepath.asynchttpclient.AsyncHttpClient;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
//import com.example.flixster.Adapters.MovieAdapter;
import com.example.flixster.Models.Movie;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //ActivityMainBinding activityMainBinding;

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    public static final String KEY_MOVIE_TITLE = "movie_title";
    public static final String KEY_MOVIE_OVERVIEW = "movie_overview";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_RATING = "movie_rating";
    public static final String KEY_MOVIE_TRAILER_ID = "movie_trailerID";
    public static final int DETAILS_CODE = 25;

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //View view = activityMainBinding.getRoot();
        //setContentView(view);
        //RecyclerView rvMovies = activityMainBinding.rvMovies;
        movies = new ArrayList<>();

        /*MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onMovieClicked(int position) {
                Log.d("MainActivity", "Click at position " + position);
                // Create a new activity
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                // Pass data into it
                i.putExtra(KEY_MOVIE_TITLE, movies.get(position).getTitle());
                i.putExtra(KEY_MOVIE_OVERVIEW, movies.get(position).getOverview());
                i.putExtra(KEY_POSTER_PATH, movies.get(position).getPosterPath());
                i.putExtra(KEY_RATING, movies.get(position).getRating());
                i.putExtra(KEY_MOVIE_TRAILER_ID, movies.get(position).getMovieTrailerId());
                // Display the activity
                startActivityForResult(i, DETAILS_CODE);
            }
        };

        // Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies, onClickListener);
        // Set adapter on the RecyclerView
        rvMovies.setAdapter(movieAdapter);
        // Set layout manager on the Recycler View
        rvMovies.setLayoutManager(new LinearLayoutManager(this)); */

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException | JsonRpcException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}