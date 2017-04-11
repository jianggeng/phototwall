package com.jgeng.photowall;

import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by jgeng on 3/10/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
  private int[]  data;
  public class ViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView imageView;
    private int[]  data;
    public ViewHolder(View v) {
      super(v);
      imageView = (SimpleDraweeView)v;
    }

  }

  private ArrayList<SearchTask.Photo> photoList = new ArrayList<>();
  public void setList(ArrayList<SearchTask.Photo> list) {
    Log.e("MyAdapter", "setList " + list.size());
    photoList = list;
    notifyDataSetChanged();
  }

  @Override
  public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
    // create a new view

    SimpleDraweeView view = new SimpleDraweeView(parent.getContext());
    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    view.setAspectRatio(1.0f);
//    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
    view.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
    return new ViewHolder(view);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    SearchTask.Photo photo = photoList.get(position);
    holder.imageView.setImageURI(photo.url);
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return photoList.size();
  }
}
