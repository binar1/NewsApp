package com.example.developer.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by binar on 11/11/2017.
 */

public class Query {

    public static List<News> getdata(String url) {
        URL link = makeurl(url);
        String jsonres;
        jsonres = httpMake(link);


        List<News> resltParse = getJsonParse(jsonres);
        return resltParse;
    }


    public static URL makeurl(String stringgurl) {
        URL url = null;
        try {

            url = new URL(stringgurl);

        } catch (MalformedURLException e) {
            System.out.print("you have url issue");
        }
        System.out.println(url);
        return url;

    }

    public static String httpMake(URL url) {
        HttpURLConnection connection = null;
        InputStream inputStreamReader = null;
        String resltConnection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                inputStreamReader = connection.getInputStream();
                resltConnection = ReadFromStream(inputStreamReader);
            }


        } catch (IOException e) {
            System.out.println("You have issuse in Stream reader");
        }

        return resltConnection;
    }

    public static String ReadFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();

            }
        }
        return stringBuilder.toString();
    }


    public static List<News> getJsonParse(String jsone) {
        List<News> jsonlist = new ArrayList<>();
        JSONObject root;
        try {
            root = new JSONObject(jsone);
            JSONObject jsonObject = root.getJSONObject("response");
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject res = jsonArray.getJSONObject(i);
                String section = "Section: ";
                String Author = "Author:";
                String title = "";
                String link = "";
                String data = "";

                if (res.has("webTitle")) {
                    title = res.getString("webTitle");
                }
                if (res.has("webUrl")) {
                    link = res.getString("webUrl");
                }
                if (res.has("webPublicationDate")) {
                    data = res.getString("webPublicationDate");
                }
                if (res.has("sectionName")) {
                    section += res.getString("sectionName");
                }
                if (res.has("tags")) {
                    if (res.getJSONArray("tags").length() > 0) {
                        JSONObject tags = res.getJSONArray("tags").getJSONObject(0);
                        if (tags.has("firstName")) {
                            Author += tags.getString("firstName");
                            Author += " " + tags.getString("lastName");
                        }


                    }
                }
                News news = new News(link, section, Author, title, data);
                jsonlist.add(news);

            }

        } catch (JSONException e) {
            Log.w(null, "You have parsin Issuse");
        }
        return jsonlist;


    }


}
