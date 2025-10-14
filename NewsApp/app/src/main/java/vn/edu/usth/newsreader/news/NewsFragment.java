package vn.edu.usth.newsreader.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.config.ApiConfig;
import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.login.User;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<Article> articles;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        articles = new ArrayList<>();

        // ĐOẠN CODE MỚI THAY THẾ 4 DÒNG TRUY VẤN DATABASE TRÊN MAIN THREAD
        Executors.newSingleThreadExecutor().execute(() -> {
            User loggedInUser = Prefs.getLoggedInUser(requireContext());
            if (loggedInUser != null) {
                int userId = loggedInUser.getId();
                new Handler(Looper.getMainLooper()).post(() -> {
                    newsAdapter = new NewsAdapter(requireContext(), articles, userId);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    recyclerView.setAdapter(newsAdapter);

                    swipeRefreshLayout.setOnRefreshListener(this::fetchNews);
                    fetchNews();
                });
            } else {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(requireContext(), "Chưa có user đăng nhập!", Toast.LENGTH_LONG).show();
                });
            }
        });

        return view;
    }

    public void refreshNow() {
        // Scroll lên đầu danh sách trước khi refresh
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
        fetchNews();
    }

    private void fetchNews() {
        // Kiểm tra API key
        if (ApiConfig.API_KEY == null || ApiConfig.API_KEY.isEmpty()) {

            Toast.makeText(requireContext(),
                    "Vui lòng cập nhật API key trong ApiConfig.java",
                    Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        swipeRefreshLayout.setRefreshing(true);

        NewsApiService apiService = NewsApiClient.getInstance().create(NewsApiService.class);
        
        // Lấy tin từ nhiều nguồn khác nhau và trộn lẫn
        fetchNewsFromMultipleSources(apiService);
    }

    private void fetchNewsFromMultipleSources(NewsApiService apiService) {
        // Lấy danh sách nguồn tin
        String[] sources = getRandomSourcesArray();
        java.util.List<Article> allArticles = new java.util.ArrayList<>();
        final int[] completedRequests = {0};
        final int totalRequests = sources.length;
        
        Log.d("NewsFragment", "Fetching from " + totalRequests + " sources: " + String.join(", ", sources));
        
        for (String source : sources) {
            Call<NewsResponse> call = apiService.getTopHeadlines(source, ApiConfig.API_KEY);
            
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getArticles() != null) {
                        allArticles.addAll(response.body().getArticles());
                        Log.d("NewsFragment", "Successfully loaded " + response.body().getArticles().size() + " articles from " + source);
                    } else {
                        Log.w("NewsFragment", "Failed to load from " + source + " - Code: " + response.code());
                    }
                    
                    completedRequests[0]++;
                    if (completedRequests[0] == totalRequests) {
                        // Tất cả requests đã hoàn thành, trộn lẫn và hiển thị
                        mixAndDisplayArticles(allArticles);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                    Log.w("NewsFragment", "Failed to load from " + source + " - Error: " + t.getMessage());
                    completedRequests[0]++;
                    if (completedRequests[0] == totalRequests) {
                        // Tất cả requests đã hoàn thành, trộn lẫn và hiển thị
                        mixAndDisplayArticles(allArticles);
                    }
                }
            });
        }
    }

    private String[] getRandomSourcesArray() {
        // Danh sách các nguồn tin tức ổn định và khả dụng
        String[] reliableSources = {
            "techcrunch", "cnn", "the-verge", "wired", "ars-technica",
            "engadget", "mashable", "recode", "the-next-web", "venturebeat"
        };
        
        // Chọn ngẫu nhiên 3-4 nguồn
        java.util.Random random = new java.util.Random();
        int numSources = 3 + random.nextInt(2); // 3-4 nguồn
        
        java.util.List<String> selectedSources = new java.util.ArrayList<>();
        for (int i = 0; i < numSources; i++) {
            String source = reliableSources[random.nextInt(reliableSources.length)];
            if (!selectedSources.contains(source)) {
                selectedSources.add(source);
            }
        }
        
        return selectedSources.toArray(new String[0]);
    }

    private void mixAndDisplayArticles(java.util.List<Article> allArticles) {
        if (allArticles.isEmpty()) {
            Toast.makeText(requireContext(), "Không thể tải tin tức từ bất kỳ nguồn nào", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        
        // Trộn lẫn các bài viết
        java.util.Collections.shuffle(allArticles);
        
        // Giới hạn số lượng bài viết hiển thị (tối đa 20)
        if (allArticles.size() > 20) {
            allArticles = allArticles.subList(0, 20);
        }
        
        articles.clear();
        articles.addAll(allArticles);
        
        Log.d("NewsFragment", "Displaying " + articles.size() + " mixed articles from multiple sources");
        
        // Cập nhật trạng thái bookmark
        Executors.newSingleThreadExecutor().execute(() -> {
            User u = Prefs.getLoggedInUser(requireContext());
            int uid = u != null ? u.getId() : -1;
            for (Article article : articles) {
                boolean bookmarked = uid != -1 && Prefs.isBookmarked(requireContext(), uid, article.getUrl());
                article.setBookmarked(bookmarked);
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                newsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                // Đảm bảo scroll lên đầu sau khi cập nhật tin tức
                if (recyclerView != null) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        });
    }


}