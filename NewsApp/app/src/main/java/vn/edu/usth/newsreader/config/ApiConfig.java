package vn.edu.usth.newsreader.config;

/**
 * Cấu hình API cho ứng dụng News Reader
 * Thay đổi API_KEY và SOURCE theo nhu cầu
 */
public class ApiConfig {
    
    // ===== CẤU HÌNH API =====
    
    // API Key mới - Thay thế bằng API key của bạn
    public static final String API_KEY = "ca9e8bb4e8484dd1855c0205b6675c61";
    
    // Nguồn tin tức mặc định
    public static final String DEFAULT_SOURCE = "techcrunch";
    
    // Các nguồn tin tức phổ biến khác
    public static final String[] AVAILABLE_SOURCES = {
        "techcrunch",
        "bbc-news", 
        "cnn",
        "the-verge",
        "wired",
        "ars-technica"
    };

    // ===== CẤU HÌNH API QUOTES =====

    // API Key cho Quotes API (API Ninjas)
    public static final String QUOTES_API_KEY = "C0mhKf462ZIk6QO4XJ0YiQ==Nd5TgCw0OyPefyJH";
    
    // ===== HƯỚNG DẪN LẤY API KEY MỚI =====
    /*
    1. Truy cập: https://newsapi.org/
    2. Đăng ký tài khoản miễn phí
    3. Vào Dashboard để lấy API key
    4. Thay thế "YOUR_NEW_API_KEY_HERE" bằng API key thực
    5. Build lại ứng dụng
    */
    
    // ===== API THAY THẾ MIỄN PHÍ =====
    /*
    Nếu NewsAPI.org không hoạt động, có thể sử dụng:
    
    1. Currents API: https://currentsapi.services/
    2. Guardian API: https://open-platform.theguardian.com/
    3. NYTimes API: https://developer.nytimes.com/
    
    Cần cập nhật NewsApiService và NewsApiClient tương ứng
    */
}
