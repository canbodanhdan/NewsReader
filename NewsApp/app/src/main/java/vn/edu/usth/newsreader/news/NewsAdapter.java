package vn.edu.usth.newsreader.news;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.concurrent.Executors;

import vn.edu.usth.newsreader.R;
import vn.edu.usth.newsreader.bookmark.BookmarkManager;
import vn.edu.usth.newsreader.history.HistoryManager;
import vn.edu.usth.newsreader.storage.Prefs;

// NewsAdapter is the Adapter that manages and displays the list of articles in the RecyclerView
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context context; // Context used to work with UI components and Intents
    private List<Article> articles; // List of articles to display in the RecyclerView
    private final HistoryManager historyManager; /** History manager, stores viewed articles */
    public int userId;

    // Constructor
    public NewsAdapter(Context context, List<Article> articles, int userId) {
        this.context = context;
        this.articles = articles;
        this.userId = userId;
        this.historyManager = new HistoryManager(context, userId); // Initialize history manager
    }

    // ViewHolder to bind UI elements of each article item
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
        ImageButton bookmarkButton; // Bookmark button

        // NewsViewHolder constructor, binds views from item_news.xml
        public NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            description = itemView.findViewById(R.id.newsDescription);
            imageView = itemView.findViewById(R.id.newsImage);
            bookmarkButton = itemView.findViewById(R.id.bookmarkButton); // Bind bookmark button

        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate article item UI from item_news.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());

        String imageUrl = article.getUrlToImage();
        if (imageUrl != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.default_image);
        }

        // Update bookmark icon based on isBookmarked state
        holder.bookmarkButton.setImageResource(
                article.isBookmarked() ? R.drawable.baseline_bookmark_1 : R.drawable.baseline_bookmark_0
        );


        // Use a background thread to query SharedPreferences
        Executors.newSingleThreadExecutor().execute(() -> {
            int userIdLocal = Prefs.getLoggedInUserId(context);
            holder.bookmarkButton.setOnClickListener(v -> {
                Executors.newSingleThreadExecutor().execute(() -> {
                    Prefs.toggleBookmark(context, userIdLocal, article);
                    article.setBookmarked(!article.isBookmarked());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        holder.bookmarkButton.setImageResource(
                                article.isBookmarked() ? R.drawable.baseline_bookmark_1 : R.drawable.baseline_bookmark_0
                        );
                    });
                });
            });
        });

        // Handle click on article item
        holder.itemView.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                int userIdLocal = Prefs.getLoggedInUserId(context);
                HistoryManager historyManager = new HistoryManager(context, userIdLocal);
                historyManager.addToHistory(article, userIdLocal);

                // Navigate to detail screen on the main thread
                new android.os.Handler(Looper.getMainLooper()).post(() -> {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("url", article.getUrl());
                    context.startActivity(intent);
                });
            });
        });
    }




    @Override
    public int getItemCount() {
//         Return total number of articles
        return articles.size();
    }
}
