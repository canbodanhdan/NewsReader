package vn.edu.usth.newsreader.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.news.Article;
import vn.edu.usth.newsreader.news.NewsAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use an Executor to handle logic on a background thread
        // Get the logged-in user's ID
        // Get the history articles by userId
        // Update UI on the main thread
        // Initialize the adapter with history articles list
        // Attach the adapter to the RecyclerView
        // Return the Fragment's view
        Executors.newSingleThreadExecutor().execute(() -> {
            int userId = Prefs.getLoggedInUserId(requireContext());
            List<Article> historyArticles = new HistoryManager(requireContext(), userId).getHistoryArticles();
            requireActivity().runOnUiThread(() -> {
                adapter = new NewsAdapter(requireContext(), historyArticles, userId);

                recyclerView.setAdapter(adapter);
            });
        });

        return view;
    }

}
