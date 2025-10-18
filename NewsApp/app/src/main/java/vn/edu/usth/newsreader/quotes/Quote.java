package vn.edu.usth.newsreader.quotes;

import com.google.gson.annotations.SerializedName;

// Quote class represents a quote, including content and author
public class Quote {

    @SerializedName("quote")
    private String quote;

    @SerializedName("author")
    private String author;

    @SerializedName("category")
    private String category;

    // Default constructor
    public Quote() {
    }

    // Getters and Setters for fields
    public String getQuote() {
        return quote != null ? quote : "";
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author != null ? author : "Unknown";
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


