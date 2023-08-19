package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebBrowser extends AppCompatActivity {
    static String TransportURL;
    private WebView webView;
    private WebSettings settings;
    private final Context context = WebBrowser.this;
    private Button buttonBack;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setTextSize(CURRENT_TEXT_SIZE);
        goWeb(TransportURL);
    }

    public void goBack(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void goWeb(String TransportURL) {
        webView = findViewById(R.id.webBrowser);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setNetworkAvailable(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(context, "Загрузка", Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(context, "Загрузка завершена", Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl(TransportURL);
    }
}