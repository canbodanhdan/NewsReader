package vn.edu.usth.newsreader.quotes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.config.ApiConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuotesFragment extends Fragment {

    private RecyclerView quotesRecyclerView;
    private QuotesAdapter quotesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Quote> allQuotes = new ArrayList<>();
    private int completedRequests = 0;
    private int totalRequests = 0;

    // List of popular and meaningful categories
    private final List<String> categories = Arrays.asList(
            "happiness", "inspirational", "love", "success", "wisdom", "friendship"
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        quotesRecyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        
        quotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        quotesAdapter = new QuotesAdapter(new ArrayList<>());
        quotesRecyclerView.setAdapter(quotesAdapter);

        // Setup pull-to-refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchQuotesFromCategories();
        });

        fetchQuotesFromCategories();

        return view;
    }

    private void fetchQuotesFromCategories() {
        swipeRefreshLayout.setRefreshing(true);
        allQuotes.clear();

        QuotesApiService apiService = QuotesApiClient.getService();
        Call<List<Quote>> call = apiService.getQuotes(ApiConfig.QUOTES_API_KEY);

        Log.d("QuotesFragment", "Request URL: " + call.request().url());

        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(@NonNull Call<List<Quote>> call, @NonNull Response<List<Quote>> response) {
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    allQuotes.addAll(response.body());
                    quotesAdapter.setQuotes(allQuotes);
                    Log.d("QuotesFragment", "✅ Loaded " + response.body().size() + " quotes");
                } else {
                    try {
                        String error = response.errorBody() != null ?
                                response.errorBody().string() : "Unknown";
                        Log.e("QuotesFragment", "❌ Code " + response.code() + ": " + error);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Quote>> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("QuotesFragment", "❌ Failed: " + t.getMessage());
            }
        });
    }

    private List<String> getRandomCategories() {
        List<String> selected = new ArrayList<>();
        List<String> available = new ArrayList<>(categories);
        Collections.shuffle(available);
        
        // Select 3-4 random categories
        int count = 3 + (int)(Math.random() * 2); // 3-4 categories
        for (int i = 0; i < Math.min(count, available.size()); i++) {
            selected.add(available.get(i));
        }
        
        return selected;
    }

    private void checkAllRequestsCompleted() {
        if (completedRequests >= totalRequests) {
            if (allQuotes.isEmpty()) {
                Toast.makeText(getContext(), "Unable to load quotes. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                // Shuffle the quotes
                Collections.shuffle(allQuotes);
                
                // Limit the number of displayed items (maximum 30 quotes)
                if (allQuotes.size() > 30) {
                    allQuotes = allQuotes.subList(0, 30);
                }
                
                quotesAdapter.setQuotes(allQuotes);
                Log.d("QuotesFragment", "Displaying " + allQuotes.size() + " mixed quotes");
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
