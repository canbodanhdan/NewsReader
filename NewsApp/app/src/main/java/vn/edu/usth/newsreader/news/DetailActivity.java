package vn.edu.usth.newsreader.news;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.newsreader.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WebView webView = findViewById(R.id.webView);

        String url = getIntent().getStringExtra("url"); // Get URL from Intent

        webView.setWebViewClient(new WebViewClient()); // Use WebViewClient to open URL in WebView instead of opening in browser

        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript support for WebView

        if (url != null) {
            webView.loadUrl(url); // If URL is valid, load URL into WebView
        }
    }
}


