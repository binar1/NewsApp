package com.example.developer.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by binar on 13/11/2017.
 */

public class NewsAsyncTaskLoader extends AsyncTaskLoader<List<News>> {
    String api;

    public NewsAsyncTaskLoader(Context context, String api) {
        super(context);
        this.api = api;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (api == null || api.isEmpty()) return null;
        List<News> news = Query.getdata(api);
        return news;
    }
}
