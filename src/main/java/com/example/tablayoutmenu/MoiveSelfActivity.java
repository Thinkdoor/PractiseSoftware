package com.example.tablayoutmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class MoiveSelfActivity extends AppCompatActivity {

    final String TAG = "MoiveSelfActivity";
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_self);
        webView = (WebView) findViewById(R.id.wv);
        Intent intent  = getIntent();
        String alt = intent.getStringExtra("alt");
        Log.d(TAG, "onCreate:  -------------------"+alt);
        webView.loadUrl(alt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
