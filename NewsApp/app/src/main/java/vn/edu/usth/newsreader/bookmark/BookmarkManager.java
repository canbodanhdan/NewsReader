package vn.edu.usth.newsreader.bookmark;

import java.util.List;
import java.util.concurrent.Executors;

import android.content.Context;
import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.news.Article;

public class BookmarkManager {

    private final Context context;
    private final int userId;

    public BookmarkManager(Context context, int userId) {
        this.context = context.getApplicationContext();
        this.userId = userId;
    }

    // Get the list of bookmarked articles
    public List<Article> getBookmarkedArticles() {
        return Prefs.getBookmarkedArticles(context, userId);
    }

    // Add or remove an article from bookmarks
    public void toggleBookmark(Article article) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Prefs.toggleBookmark(context, userId, article);
        });
    }
}
