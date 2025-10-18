package vn.edu.usth.newsreader.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    // Endpoint for NewsAPI.org - get news from multiple sources
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );
    
    // Endpoint to get news by country (instead of by specific source)
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlinesByCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    
    // Alternative endpoint for Currents API (free)
    // @GET("latest-news")
    // Call<NewsResponse> getLatestNews(
    //         @Query("apiKey") String apiKey,
    //         @Query("language") String language
    // );
}