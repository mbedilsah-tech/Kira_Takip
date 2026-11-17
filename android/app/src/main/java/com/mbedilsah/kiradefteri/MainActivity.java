package com.mbedilsah.kiradefteri;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Kira Defteri'ni GitHub Pages'ten açan ince bir kabuk.
 * Uygulamanın kendisi internetteki sayfadır; GitHub'daki index.html güncellenince
 * bu uygulama da yeni APK kurmadan güncellenir.
 */
public class MainActivity extends Activity {

    static final String APP_URL = "https://mbedilsah-tech.github.io/Kira_Takip/";
    static final String APP_HOST = "mbedilsah-tech.github.io";

    static final String OFFLINE_HTML =
        "<!DOCTYPE html><html lang='tr'><head><meta name='viewport' content='width=device-width,initial-scale=1'>"
        + "<style>body{font-family:sans-serif;margin:0;padding:32px 20px;color:#102A25;background:#fff;font-size:20px;line-height:1.5}"
        + "button{font:inherit;font-weight:700;width:100%;min-height:60px;margin-top:24px;border:0;border-radius:12px;background:#0B5F51;color:#fff}</style>"
        + "</head><body><h2>İnternet bağlantısı yok</h2>"
        + "<p>Kira Defteri ilk açılışta internete bağlanmalı. Wi-Fi ya da mobil veriyi açıp tekrar deneyin.</p>"
        + "<button onclick='KiraAndroid.reload()'>Tekrar dene</button></body></html>";

    private WebView web;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.parseColor("#0B5F51"));

        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.addJavascriptInterface(new Bridge(), "KiraAndroid");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                Uri u = req.getUrl();
                if (APP_HOST.equalsIgnoreCase(u.getHost())) return false;
                try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception ignored) { }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
                if (req.isForMainFrame()) {
                    view.loadDataWithBaseURL(null, OFFLINE_HTML, "text/html", "utf-8", null);
                }
            }
        });

        setContentView(web);
        if (state != null) web.restoreState(state);
        else web.loadUrl(APP_URL);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }

    /** Sayfanın çağırabildiği küçük yardımcılar: panoya kopyalama ve yeniden yükleme. */
    class Bridge {
        @JavascriptInterface
        public void copy(String text) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) cm.setPrimaryClip(ClipData.newPlainText("Kira Defteri", text));
        }

        @JavascriptInterface
        public void reload() {
            runOnUiThread(() -> web.loadUrl(APP_URL));
        }
    }
}
