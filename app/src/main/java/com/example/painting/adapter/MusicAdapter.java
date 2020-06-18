package com.example.painting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.painting.R;
import com.example.painting.object.Music;

import java.util.List;


public class MusicAdapter extends ArrayAdapter<Music> {

    private int id;

    public MusicAdapter(Context context, int resource, List<Music> music) {
        super(context, resource,music);
        id = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music music = getItem(position);
        //make xml into view
        View view = LayoutInflater.from(getContext()).inflate(id,parent,false);
        ImageView imageView = view.findViewById(R.id.music_image);
        TextView title = view.findViewById(R.id.title);
        TextView singer = view.findViewById(R.id.singer);
        imageView.setImageResource(music.getImage());
        title.setText(music.getTitle());
        singer.setText(music.getSinger());
        return view;
    }
}
