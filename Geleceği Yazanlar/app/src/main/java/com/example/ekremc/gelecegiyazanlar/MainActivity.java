package com.example.ekremc.gelecegiyazanlar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements DownloadTask.AsyncResponse {
    ListView lv;
    WebView webView;
    Context context;
    DownloadTask task = new DownloadTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        webView = (WebView) findViewById(R.id.webView);

        task.delegate = this;
        task.execute("https://gelecegiyazanlar.turkcell.com.tr/gypservis/article/retrieve?nitems=10&index=0&kategoriID=718");


    }


    @Override
    public void processFinish() {
        context = this;

        lv.setAdapter(new CustomAdapter(this, task.title, task.images));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://gelecegiyazanlar.turkcell.com.tr/gypservis/article_content/retrieve?nodeID=" + task.id.get(i));
            }
        });
    }

    public void onBackPressed() {
        webView.setVisibility(View.INVISIBLE);
    }

}
