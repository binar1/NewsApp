package com.example.developer.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.developer.newsapp.R.layout.news;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    String NEWS_API = "https://content.guardianapis.com/search?q=debates&api-key=test&show-tags=contributor&order-by=newest";
    TextView badresponse;
    NewsArrayAdapter adapter;
    LoaderManager loaderManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.reload);
        swipeRefreshLayout.setRefreshing(true);
        excute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = adapter.getItem(i);
                Uri uri = Uri.parse(news.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                excute();
            }
        });

    }

    public void excute() {
        listView = (ListView) findViewById(R.id.list_item);
        ArrayList<News> array = new ArrayList<>();
        adapter = new NewsArrayAdapter(MainActivity.this, news, array);
        badresponse = (TextView) findViewById(R.id.badresponse);
        listView.setEmptyView(badresponse);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
        if (isNetwork(this)) {
            loaderManager = getLoaderManager();
            loaderManager.restartLoader(0, null, this);
        } else {
            Log.w(null, "you have network issus");

        }
    }


    public boolean isNetwork(Context context) {
        ConnectivityManager network = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = network.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsAsyncTaskLoader(this, NEWS_API);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        adapter.clear();
        if (newses == null || newses.isEmpty())
            return;
        adapter.addAll(newses);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();

    }
}
