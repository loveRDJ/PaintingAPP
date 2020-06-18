package com.example.painting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.painting.adapter.PaintAdapter;
import com.example.painting.object.MyPaint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

//https://www.youtube.com/watch?v=O6dWwoULFI8&t=9s
//https://www.youtube.com/watch?v=LpL9akTG4hI
//https://www.youtube.com/watch?v=u5PDdg1G4Q4&t=3s

public class PaintActivity extends AppCompatActivity {

    ArrayList<MyPaint> myPaints = new ArrayList<>();
    private String background="white";

    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAMERA = 1;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        initPaint();

        PaintAdapter adapter = new PaintAdapter(PaintActivity.this,R.layout.paint, myPaints);
        GridView listView = findViewById(R.id.grid_view_paint);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyPaint myPaint = myPaints.get(i);
                if(myPaint.getName().equalsIgnoreCase("pika")){
                    background="pika";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }else if (myPaint.getName().equalsIgnoreCase("car")){
                    background="car";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }else if (myPaint.getName().equalsIgnoreCase("snail")){
                    background="snail";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }else  if (myPaint.getName().equalsIgnoreCase("tree")){
                    background="tree";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }else if (myPaint.getName().equalsIgnoreCase("owl")){
                    background="owl";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }else if (myPaint.getName().equalsIgnoreCase("dog")){
                    background="dog";
                    Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                    intent.putExtra("background",background);
                    startActivity(intent);
                }
            }
        });

    }

    public void initPaint(){
        MyPaint car = new MyPaint("car",R.drawable.car);
        myPaints.add(car);
        MyPaint snail = new MyPaint("snail",R.drawable.snail);
        myPaints.add(snail);
        MyPaint pika = new MyPaint("pika",R.drawable.pika);
        myPaints.add(pika);
        MyPaint tree = new MyPaint("tree",R.drawable.tree);
        myPaints.add(tree);
        MyPaint owl = new MyPaint("owl",R.drawable.owl);
        myPaints.add(owl);
        MyPaint dog = new MyPaint("dog",R.drawable.dog);
        myPaints.add(dog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_import,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blank:
                Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
                intent.putExtra("background",background);
                startActivity(intent);
                return true;
            case R.id.album:
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,PICK_IMAGE);
                return true;
            case R.id.camera:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,REQUEST_CAMERA);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            imageUri = data.getData();
            background="photo";
            Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
            intent.putExtra("background",background);
            intent.putExtra("photo",imageUri.toString());
            startActivity(intent);
        }else if (requestCode==REQUEST_CAMERA && resultCode==RESULT_OK){
            background="camera";
            Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageUri = convertToUri(bitmap);
            intent.putExtra("camera",imageUri.toString());
//            Bundle bundle = new Bundle();
//            bundle.putBundle("data",data.getExtras());
//            Intent intent = new Intent(PaintActivity.this,BlankActivity.class);
//            intent.putExtra("camera", bundle);
            intent.putExtra("background",background);
            startActivity(intent);
        }
    }

    public Uri convertToUri(Bitmap bitmap){
        File path = new File(getCacheDir()+File.separator+System.currentTimeMillis()+".jpg");
        try {
            OutputStream outputStream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            return Uri.fromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
