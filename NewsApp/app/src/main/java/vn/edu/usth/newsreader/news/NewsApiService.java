package vn.edu.usth.newsreader.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    // Endpoint for NewsAPI.org — fetches top headlines from specific sources
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );
    
    //  Endpoint for NewsAPI.org — fetches top headlines based on country code.
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlinesByCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    
    // Endpoint replace for Currents API (free)
    // @GET("latest-news")
    // Call<NewsResponse> getLatestNews(
    //         @Query("apiKey") String apiKey,
    //         @Query("language") String language
    // );
}