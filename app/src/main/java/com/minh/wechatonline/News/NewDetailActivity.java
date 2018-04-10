package com.minh.wechatonline.News;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.minh.wechatonline.R;

public class NewDetailActivity extends AppCompatActivity {
    private android.support.v7.app.ActionBar actionBar;
    private WebView webViewTruyen;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        webViewTruyen = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("LINK");
        if(url !=null){
            dialog = new ProgressDialog(NewDetailActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
            webViewTruyen.setWebViewClient(onWebViewClient);
            webViewTruyen.loadUrl(url);
        }
    }
    private WebViewClient onWebViewClient = new WebViewClient(){
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dialog.dismiss();
        };
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            dialog.dismiss();
            Toast.makeText(NewDetailActivity.this, "Have some error", Toast.LENGTH_LONG).show();
        };
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
