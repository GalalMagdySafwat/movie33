package com.example.ga.movieapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class MovieUtils {
    private static final String LOG_TAG = MovieUtils.class.getSimpleName();

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder outPut = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                outPut.append(line);
                line = reader.readLine();
            }
        }
        return outPut.toString();
    }

    private static List<Movie> extractFeatureFromJson(String movieJSON) {
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {


                JSONObject currentMovies = resultsArray.getJSONObject(i);


                String originalTitle = currentMovies.getString("title");
                String moviePoster = "http://image.tmdb.org/t/p/w342/"+currentMovies.getString("poster_path");
                String overView = "Story : "+ currentMovies.getString("overview");
                String userRating = "Average vote : "+currentMovies.getString("vote_average");
                String releaseDate ="Release Date : " +currentMovies.getString("release_date");
                String movieID= currentMovies.getString("id");


                Movie movie = new Movie(originalTitle, moviePoster, overView, userRating, releaseDate,movieID, null, null);
                movies.add(movie);

            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }
        return movies;
    }
    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url=createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Movie> movies =extractFeatureFromJson(jsonResponse);
        return movies;
    }


    private static List<Reviews> extractReviewsFromJson(String movieJSON) {
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        List<Reviews> reviews = new ArrayList<>();
        try {


            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONObject videoObject = baseJsonResponse.getJSONObject("reviews");
            JSONArray resultsArray = videoObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {


                JSONObject currentVideo = resultsArray.getJSONObject(i);


                String author = currentVideo.getString("author");
                String content = currentVideo.getString("content");


                    Reviews review = new Reviews(author,content);

                    reviews.add(review);

            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }
        return reviews;
    }
    public static List<Reviews> fetchReviewsData(String requestUrl) {
        URL url=createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Reviews> reviews = extractReviewsFromJson(jsonResponse);
        return reviews;
    }


    private static List<Videos> extractVideosFromJson(String movieJSON) {
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        List<Videos> videos = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONObject videoObject = baseJsonResponse.getJSONObject("videos");
            JSONArray resultsArray = videoObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {


                JSONObject currentVideo = resultsArray.getJSONObject(i);


                String key = currentVideo.getString("key");

                Videos video = new Videos(key);

                    videos.add(video);
                }


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }
        return videos;
    }
    public static List<Videos> fetchVideosData(String requestUrl) {
        URL url=createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Videos> Videos =extractVideosFromJson(jsonResponse);
        return Videos;
    }

}
