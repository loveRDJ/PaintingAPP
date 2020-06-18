package com.example.painting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.painting.R;
import com.example.painting.object.MyPaint;

import java.util.List;


public class PaintAdapter extends ArrayAdapter<MyPaint> {

    private int id;

    public PaintAdapter(Context context, int resource, List<MyPaint> myPaints) {
        super(context, resource, myPaints);
        id = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyPaint myPaint = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(id,parent,false);
        ImageView imageView = view.findViewById(R.id.paint_image);
        imageView.setImageResource(myPaint.getImage());
        return view;
    }
}
