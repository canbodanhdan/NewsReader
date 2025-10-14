package vn.edu.usth.newsreader.history;

import android.content.Context;

import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.news.Article;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryManager {

    // Biến context để thao tác với SharedPreferences
    // ID của người dùng hiện tại, dùng để lọc lịch sử theo người dùng
    private final Context context;
    private final int userId;

    // Constructor để khởi tạo HistoryManager
    // - context: Dùng để truy cập SharedPreferences.
    // - userId: ID của người dùng, giúp quản lý lịch sử riêng của từng người.
    public HistoryManager(Context context, int userId) {
        this.context = context.getApplicationContext();
        this.userId = userId;
    }

    // Phương thức thêm bài viết vào lịch sử
    // - article: Đối tượng bài viết cần thêm.
    // - userId: ID người dùng để gán bài viết vào đúng lịch sử của họ
    public void addToHistory(Article article, int userId) {

        // Sử dụng một Executor để chạy tác vụ trong luồng riêng, tránh chặn giao diện chính (Main Thread).
        Executors.newSingleThreadExecutor().execute(() -> {

            // Thêm bài viết vào lịch sử thông qua SharedPreferences

            Prefs.addToHistory(context, userId, article);
        });
    }

    // Phương thức lấy danh sách các bài viết trong lịch sử
    // Trả về danh sách các bài viết đã được lưu vào lịch sử của người dùng hiện tại
    public List<Article> getHistoryArticles() {
        return Prefs.getHistoryArticles(context, userId);
    }
}

