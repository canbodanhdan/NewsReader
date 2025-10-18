package vn.edu.usth.newsreader.news;

import java.util.List;

// NewsResponse class represents the response from API when requesting a list of articles.
// It contains properties reflecting status, total results and list of articles.
public class NewsResponse {

    // Status property represents the status of the API response (e.g. "ok" or "error").
    private String status;

    // TotalResults property indicates the total number of articles returned from API.
    private int totalResults;

    // Articles property contains the list of articles returned from API, each article is an Article object.
    private List<Article> articles;

    // Constructor
    public NewsResponse(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

