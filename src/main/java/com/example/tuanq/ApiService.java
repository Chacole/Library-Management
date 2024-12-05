package com.example.tuanq;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private static final String GOOGLE_BOOKS_API_KEY = "AIzaSyBz2FeG1-5s9CUnpx7y0kYcbEzLk54SmNo";
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static String getGoogleBookImage(String query) {
        String requestUrl = GOOGLE_BOOKS_API_URL + query + "&key=" + GOOGLE_BOOKS_API_KEY;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(requestUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray items = jsonResponse.optJSONArray("items");
            if (items != null && items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                if (imageLinks != null) {
                    return imageLinks.getString("thumbnail");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}