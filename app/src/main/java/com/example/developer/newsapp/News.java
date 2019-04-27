package com.example.developer.newsapp;

/**
 * Created by binar on 11/11/2017.
 */

public class News {
    private String Link;
    private String section;
    private String author;
    private String title;
    private String date;

    public News(String Link, String section, String author, String title, String date) {
        this.Link = Link;
        this.section = section;
        this.title = title;
        this.date = date;
        this.author = author;
    }

    public String getLink() {
        return Link;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
