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

        String url = getIntent().getStringExtra("url"); // Receive the URL from the Intent

        webView.setWebViewClient(new WebViewClient());
        // Use WebViewClient to open URLs inside the WebView instead of an external browser

        webView.getSettings().setJavaScriptEnabled(true);// Enable JavaScript support in WebView

        if (url != null) {
            webView.loadUrl(url);  // Load the URL if it is valid
        }
    }
}


