package vn.edu.usth.newsreader.news;

public class Article {

    private int id;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private boolean isHistory;    // Additional field to manage history
    private int userId;     // Additional field to link with user
    private boolean isBookmarked; // Used for Bookmark
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmark) {
        isBookmarked = bookmark;
    }
}
