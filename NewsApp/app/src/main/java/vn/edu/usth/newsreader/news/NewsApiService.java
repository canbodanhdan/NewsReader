package vn.edu.usth.newsreader.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    // Endpoint cho NewsAPI.org - lấy tin từ nhiều nguồn
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );
    
    // Endpoint lấy tin theo quốc gia (thay vì theo nguồn cụ thể)
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlinesByCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    
    // Endpoint thay thế cho Currents API (miễn phí)
    // @GET("latest-news")
    // Call<NewsResponse> getLatestNews(
    //         @Query("apiKey") String apiKey,
    //         @Query("language") String language
    // );
}