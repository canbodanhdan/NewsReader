package vn.edu.usth.newsreader.quotes;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface QuotesApiService {

    // Get random quotes (no category needed)
    @GET("v1/quotes")
    Call<List<Quote>> getQuotes(@Header("X-Api-Key") String apiKey);
}