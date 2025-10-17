package vn.edu.usth.newsreader.config;

/**
 * API configuration for the News Reader application.
 * Modify API_KEY and SOURCE as needed.
 */
public class ApiConfig {

    // ===== API CONFIGURATION =====

    // New API Key - Replace with your own API key
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

// ===== INSTRUCTIONS TO GET A NEW API KEY =====
    /*
    1. Go to: https://newsapi.org/
    2. Sign up for a free account
    3. Access the Dashboard to obtain your API key
    4. Replace "YOUR_NEW_API_KEY_HERE" with the actual API key
    5. Rebuild the application
    */

// ===== FREE ALTERNATIVE APIs =====
    /*
    If NewsAPI.org is unavailable, you can use:

    1. Currents API: https://currentsapi.services/
    2. Guardian API:
