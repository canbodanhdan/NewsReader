package vn.edu.usth.newsreader.news;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NewsApiClient {

    // Using NewsAPI.org with new API key
    private static final String BASE_URL = "https://newsapi.org/v2/";
    
    // Or use free alternative API
    // private static final String BASE_URL = "https://api.currentsapi.services/v1/";
    
    private static Retrofit retrofit = null;

    // Method returns initialized Retrofit instance
    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Create OkHttpClient with logging and User-Agent
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        okhttp3.Request original = chain.request();
                        okhttp3.Request request = original.newBuilder()
                                .header("User-Agent", "NewsApp/1.0")
                                .build();
                        return chain.proceed(request);
                    })
                    .build();
            
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}