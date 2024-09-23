package com.example.seebuses.pages;

import static com.example.seebuses.core.data.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.core.data.SearchData.getSchemaURI_YandexMetro;
import static com.example.seebuses.core.data.SearchData.getTransportURI_BUSTI;
import static com.example.seebuses.core.data.SearchData.getTransportURI_IGIS;
import static com.example.seebuses.core.data.SearchData.getTransportURI_KUDIKINO;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seebuses.R;
import com.example.seebuses.core.data.TransportType;
import com.example.seebuses.core.entities.BlockElement;
import com.example.seebuses.core.entities.WheelTransport;

public class WebBrowser extends AppCompatActivity {

    private static String URL;
    private WebView webView;
    private WebSettings settings;
    private Button buttonBack;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        buttonBack = findViewById(R.id.buttonBack);
        progressBar = findViewById(R.id.progressBar);
        buttonBack.setTextSize(CURRENT_TEXT_SIZE);
        goWeb(URL);
    }

    @Override
    public void onBackPressed() {
        goBack(this.getCurrentFocus());
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
        settings.setDefaultFontSize(CURRENT_TEXT_SIZE);
        webView.setNetworkAvailable(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.animate().alpha(0.0f);
                progressBar.animate().setDuration(750);
                super.onPageStarted(view, url, favicon);
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                view.reload();
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.animate().start();
            }
        });
        webView.loadUrl(TransportURL);
    }

    static void getURL(BlockElement tb) {
        if (!tb.getType().equals(TransportType.metro)) {
            final String city = tb.getCity();
            if (!city.equals("Ижевск") && !city.equals("Izhevsk")) {
                URL = getTransportURI_BUSTI((WheelTransport) tb);
            } else {
                if (!tb.getType().equals(TransportType.citybus)) {
                    URL = getTransportURI_IGIS((WheelTransport) tb);
                } else {
                    URL = getTransportURI_KUDIKINO((WheelTransport) tb);
                }
            }
        } else URL = getSchemaURI_YandexMetro(tb);
    }
}