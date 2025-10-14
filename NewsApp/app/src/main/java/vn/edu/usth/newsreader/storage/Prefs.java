package vn.edu.usth.newsreader.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.newsreader.login.User;
import vn.edu.usth.newsreader.news.Article;

/**
 * Lightweight storage based on SharedPreferences. This replaces Room usage.
 * Data model:
 * - Users: stored as JSON array under USERS_JSON
 * - Logged-in user id: LOGGED_IN_USER_ID
 * - Bookmarks per user: BOOKMARKS_{userId} stored as JSON array of Article
 * - History per user: HISTORY_{userId} stored as JSON array of Article
 */
public final class Prefs {
    private static final String FILE = "news_prefs";
    private static final String USERS_JSON = "users_json";
    private static final String LOGGED_IN_USER_ID = "logged_in_user_id";

    private static final Gson gson = new Gson();

    private Prefs() { }

    private static SharedPreferences sp(Context context) {
        return context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    // ===== User management =====
    public static synchronized boolean register(Context context, String email, String password) {
        List<User> users = loadUsers(context);
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return false;
        }
        User u = new User(email, password, false);
        u.setId(generateNextUserId(users));
        users.add(u);
        saveUsers(context, users);
        return true;
    }

    public static synchronized User login(Context context, String email, String password) {
        List<User> users = loadUsers(context);
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                u.setLoggedIn(true);
                saveUsers(context, users);
                sp(context).edit().putInt(LOGGED_IN_USER_ID, u.getId()).apply();
                return u;
            }
        }
        return null;
    }

    public static synchronized void logout(Context context) {
        int id = getLoggedInUserId(context);
        if (id != -1) {
            List<User> users = loadUsers(context);
            for (User u : users) {
                if (u.getId() == id) {
                    u.setLoggedIn(false);
                    break;
                }
            }
            saveUsers(context, users);
        }
        sp(context).edit().remove(LOGGED_IN_USER_ID).apply();
    }

    public static int getLoggedInUserId(Context context) {
        return sp(context).getInt(LOGGED_IN_USER_ID, -1);
    }

    public static User getLoggedInUser(Context context) {
        int id = getLoggedInUserId(context);
        if (id == -1) return null;
        for (User u : loadUsers(context)) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    private static List<User> loadUsers(Context context) {
        String json = sp(context).getString(USERS_JSON, "");
        if (json == null || json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> users = gson.fromJson(json, type);
        return users != null ? users : new ArrayList<>();
    }

    private static void saveUsers(Context context, List<User> users) {
        sp(context).edit().putString(USERS_JSON, gson.toJson(users)).apply();
    }

    private static int generateNextUserId(List<User> users) {
        int max = 0;
        for (User u : users) max = Math.max(max, u.getId());
        return max + 1;
    }

    // ===== Bookmarks and History per user =====
    private static String keyBookmarks(int userId) { return "bookmarks_" + userId; }
    private static String keyHistory(int userId) { return "history_" + userId; }

    public static synchronized List<Article> getBookmarkedArticles(Context context, int userId) {
        String json = sp(context).getString(keyBookmarks(userId), "");
        if (json == null || json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<Article>>(){}.getType();
        List<Article> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }

    public static synchronized void toggleBookmark(Context context, int userId, Article article) {
        List<Article> list = getBookmarkedArticles(context, userId);
        int idx = indexOfArticleByUrl(list, article.getUrl());
        if (idx >= 0) {
            list.remove(idx);
        } else {
            Article copy = copyArticle(article);
            copy.setBookmarked(true);
            copy.setUserId(userId);
            list.add(copy);
        }
        sp(context).edit().putString(keyBookmarks(userId), gson.toJson(list)).apply();
    }

    public static synchronized boolean isBookmarked(Context context, int userId, String url) {
        List<Article> list = getBookmarkedArticles(context, userId);
        return indexOfArticleByUrl(list, url) >= 0;
    }

    public static synchronized List<Article> getHistoryArticles(Context context, int userId) {
        String json = sp(context).getString(keyHistory(userId), "");
        if (json == null || json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<Article>>(){}.getType();
        List<Article> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }

    public static synchronized void addToHistory(Context context, int userId, Article article) {
        List<Article> list = getHistoryArticles(context, userId);
        // prevent duplicates by URL
        if (indexOfArticleByUrl(list, article.getUrl()) < 0) {
            Article copy = copyArticle(article);
            copy.setHistory(true);
            copy.setUserId(userId);
            list.add(copy);
            sp(context).edit().putString(keyHistory(userId), gson.toJson(list)).apply();
        }
    }

    private static int indexOfArticleByUrl(List<Article> list, String url) {
        if (url == null) return -1;
        for (int i = 0; i < list.size(); i++) {
            if (url.equals(list.get(i).getUrl())) return i;
        }
        return -1;
    }

    private static Article copyArticle(Article src) {
        Article a = new Article();
        a.setId(src.getId());
        a.setTitle(src.getTitle());
        a.setDescription(src.getDescription());
        a.setUrl(src.getUrl());
        a.setUrlToImage(src.getUrlToImage());
        a.setBookmarked(src.isBookmarked());
        a.setHistory(src.isHistory());
        a.setUserId(src.getUserId());
        return a;
    }
}


