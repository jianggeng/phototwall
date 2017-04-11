package com.jgeng.photowall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  MyAdapter adapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.e("PhotoWall", "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final RecyclerView rv = (RecyclerView)findViewById(R.id.my_recycler_view);
    SearchView searchView = (SearchView)findViewById(R.id.search_view);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        new SearchTask(s, new SearchTask.Callback() {
          @Override
          public void onFailure() {
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
          }

          @Override
          public void onSuccess(ArrayList<SearchTask.Photo> result) {
            rv.scrollToPosition(0);
            adapter.setList(result);
          }
        });
        return false;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });


    adapter = new MyAdapter();
    rv.setAdapter(adapter);
    LinearLayoutManager lm = new LinearLayoutManager(this);
    rv.setLayoutManager(lm);
  }

  @Override
  public void onBackPressed() {
    moveTaskToBack(false);
  }
}
