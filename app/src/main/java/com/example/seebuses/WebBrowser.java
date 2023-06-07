package com.example.seebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowser extends AppCompatActivity {
    static String TransportURL;
    private static WebView webView;
    private static WebSettings settings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        goWeb(TransportURL);

    }

    public void goBack(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }
    @SuppressLint("SetJavaScriptEnabled")
    public void goWeb(String TransportURL) {
        webView = findViewById(R.id.webBrowser);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(TransportURL);
    }
}