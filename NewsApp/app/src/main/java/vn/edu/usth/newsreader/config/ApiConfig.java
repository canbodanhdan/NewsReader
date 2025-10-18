package vn.edu.usth.newsreader.config;

/**
 * API configuration for News Reader application
 * Change API_KEY and SOURCE as needed
 */
public class ApiConfig {
    
    // ===== API CONFIGURATION =====
    
    // New API Key - Replace with your API key
    public static final String API_KEY = "ca9e8bb4e8484dd1855c0205b6675c61";
    
    // Default news source
    public static final String DEFAULT_SOURCE = "techcrunch";
    
    // Other popular news sources
    public static final String[] AVAILABLE_SOURCES = {
        "techcrunch",
        "bbc-news", 
        "cnn",
        "the-verge",
        "wired",
        "ars-technica"
    };

    // ===== QUOTES API CONFIGURATION =====

    // API Key for Quotes API (API Ninjas)
    public static final String QUOTES_API_KEY = "C0mhKf462ZIk6QO4XJ0YiQ==Nd5TgCw0OyPefyJH";
    
    // ===== GUIDE TO GET NEW API KEY =====
    /*
    1. Visit: https://newsapi.org/
    2. Register for a free account
    3. Go to Dashboard to get API key
    4. Replace "YOUR_NEW_API_KEY_HERE" with actual API key
    5. Rebuild the application
    */
    
    // ===== FREE ALTERNATIVE APIs =====
    /*
    If NewsAPI.org is not working, you can use:
    
    1. Currents API: https://currentsapi.services/
    2. Guardian API: https://open-platform.theguardian.com/
    3. NYTimes API: https://developer.nytimes.com/
    
    Need to update NewsApiService and NewsApiClient accordingly
    */
}
