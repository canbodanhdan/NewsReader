package vn.edu.usth.newsreader.quotes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// QuotesApiClient creates and provides an instance of QuotesApiService.
public class QuotesApiClient {
    private static QuotesApiService service = null;

    // getService method returns instance of QuotesApiService.
    public static QuotesApiService getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.api-ninjas.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(QuotesApiService.class);
        }
        return service;
    }
}
