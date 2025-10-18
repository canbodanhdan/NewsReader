package vn.edu.usth.newsreader.history;

import android.content.Context;

import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.news.Article;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryManager {

    // Context variable to work with SharedPreferences
    // Current user ID, used to filter history by user
    private final Context context;
    private final int userId;

    // Constructor to initialize HistoryManager
    // - context: Used to access SharedPreferences.
    // - userId: User ID, helps manage individual user history.
    public HistoryManager(Context context, int userId) {
        this.context = context.getApplicationContext();
        this.userId = userId;
    }

    // Method to add article to history
    // - article: Article object to add.
    // - userId: User ID to assign article to their correct history
    public void addToHistory(Article article, int userId) {

        // Use an Executor to run tasks in a separate thread, avoiding blocking the main UI thread.
        Executors.newSingleThreadExecutor().execute(() -> {

            // Add article to history through SharedPreferences

            Prefs.addToHistory(context, userId, article);
        });
    }

    // Method to get list of articles in history
    // Returns list of articles saved in current user's history
    public List<Article> getHistoryArticles() {
        return Prefs.getHistoryArticles(context, userId);
    }
}

