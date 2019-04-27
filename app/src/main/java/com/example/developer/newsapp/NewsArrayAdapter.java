package com.example.developer.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by binar on 11/11/2017.
 */

public class NewsArrayAdapter extends ArrayAdapter<News> {

    public NewsArrayAdapter(Context context, int resources, List<News> objects) {
        super(context, resources, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.news, parent, false);
        }
        News news = getItem(position);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        TextView date = (TextView) listItem.findViewById(R.id.date);
        TextView section = (TextView) listItem.findViewById(R.id.section);
        TextView author = (TextView) listItem.findViewById(R.id.author);
        title.setText(news.getTitle());
        date.setText(news.getDate());
        section.setText(news.getSection());
        author.setText(news.getAuthor());

        return listItem;
    }
}
