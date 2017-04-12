package com.jgeng.photowall;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by jgeng on 3/10/17.
 */

public class SearchTask {
  private static final String TAG = "SearchTask";
  private static final String URL_BASE = "https://api.flickr.com/services/rest/?api_key=ad086ec0296113c74b6ecc70fb401a85&format=json";
  private static final String API_SEARCH="&method=flickr.photos.search";
  public interface Callback {
    void onFailure();
    void onSuccess(ArrayList<Photo> result);
  }

  public class Photo {
    String id;
    String secret;
    String url;
  }
  private String getJson(Response response) throws IOException {
    String str = response.body().string();
    String rtn = str.substring(str.indexOf('{'), str.lastIndexOf('}')+1);
    return rtn;
  }
  public SearchTask(final String text, final Callback callback) {
    OkHttpClient client = new OkHttpClient();
    StringBuilder builder = new StringBuilder();
    builder.append(URL_BASE);
    builder.append(API_SEARCH);

    builder.append("&text="+text);
      Request request = new Request.Builder()
          .url(builder.toString())
          .build();

      client.newCall(request).enqueue(new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          Log.e(TAG, "onFailure " + e.toString());
          Handler handler = new Handler(Looper.getMainLooper());
          handler.post(new Runnable() {
            @Override
            public void run() {
              callback.onFailure();
            }
          });

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
          Log.e(TAG, "onResponse " + response.toString());
          try {
            JSONObject json = new JSONObject(getJson(response));
            JSONArray jsonArray = json.getJSONObject("photos").getJSONArray("photo");
            final ArrayList<Photo> list = new ArrayList<>();
            for(int i =0;i<jsonArray.length();i++) {
              JSONObject jo = jsonArray.getJSONObject(i);
              Log.v(TAG, "photo " + jo.toString());
              Photo p = new Photo();
              p.id = jo.getString("id");
              p.secret = jo.getString("secret");
              p.url = String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg",
                  jo.getString("farm"),
                  jo.getString("server"),
                  jo.getString("id"),
                  jo.getString("secret"));
              list.add(p);
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
              @Override
              public void run() {
                callback.onSuccess(list);
              }
            });
          } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            callback.onFailure();
            return;
          }
        }
      });
  }
}
