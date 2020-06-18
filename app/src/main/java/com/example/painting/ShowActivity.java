package com.example.painting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private ImageButton pre,next;
    private ImageView switcher;
    private ArrayList<String> result = new ArrayList<>();
    private int count;
    private int currentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        pre = findViewById(R.id.pre);
        next = findViewById(R.id.next);
        switcher = findViewById(R.id.imagesw);

        getPhotos(getApplicationContext());

        count = result.size();
        System.out.println(count);

//        Intent intent = new Intent(this, WidgetService.class);
//        intent.putExtra("photos", result);
//        intent.putStringArrayListExtra("photos",result);


        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex--;
                if (currentIndex<0){
                    currentIndex = count-1;

                }
                System.out.println(currentIndex+"[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
                switcher.setImageURI(Uri.parse(result.get(currentIndex)));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex++;
                if (currentIndex == count){
                    currentIndex = 0;

                }
                System.out.println(currentIndex+"[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
                switcher.setImageURI(Uri.parse(result.get(currentIndex)));
            }
        });

    }

    public List<String> getPhotos(Context context){
//https://blog.csdn.net/TGWhuli/article/details/97752731
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        if (cursor==null || cursor.getCount()<=0){
            return null;
        }else {
            while (cursor.moveToNext()){
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String path = cursor.getString(index);//get uri
                File file = new File(path);
                if (file.exists()){
                    result.add(path);
                }
            }
        }
        return result;
    }
}
