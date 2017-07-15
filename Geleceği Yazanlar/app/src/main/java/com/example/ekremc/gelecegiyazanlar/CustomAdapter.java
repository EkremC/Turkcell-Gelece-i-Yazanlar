package com.example.ekremc.gelecegiyazanlar;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<String> titleAdapter = new ArrayList<>();
    ArrayList<Bitmap> imageAdapter = new ArrayList<>();
    Context context;

    public CustomAdapter(Activity activity, ArrayList<String> titles, ArrayList<Bitmap> images) {
        // TODO Auto-generated constructor stub
        titleAdapter.addAll(titles);
        context = activity;
        imageAdapter.addAll(images);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titleAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.beginning_layout, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView);
        holder.tv.setText(titleAdapter.get(position));
        holder.img.setImageBitmap(imageAdapter.get(position));

        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

}
