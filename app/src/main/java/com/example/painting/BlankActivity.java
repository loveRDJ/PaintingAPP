package com.example.painting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.painting.database.DBHelper;
import com.example.painting.dialog.BrushDialog;
import com.example.painting.dialog.ColorPickerDialog;
import com.example.painting.dialog.MusicDialog;
import com.example.painting.music.MusicCanon;
import com.example.painting.music.MusicDream;
import com.example.painting.music.MusicKiss;
import com.example.painting.music.MusicRiver;
import com.example.painting.music.MusicStory;
import com.example.painting.music.MusicSummer;
import com.example.painting.music.MusicTruth;
import com.example.painting.object.HandWrite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


//https://www.jianshu.com/p/1d11522ed35e

public class BlankActivity extends AppCompatActivity implements ColorPickerDialog.ColorPickerDialogListener, BrushDialog.BrushDialogListener, SensorEventListener {

    private HandWrite handWrite;

    private Button brush,color,eraser,music;

    private LinearLayout background;

    private int paintColor=Color.BLACK;
    private int brushSize=2;
    private int temp;

    private ToggleButton mute;

    private Intent canon;
    private Intent dream;
    private Intent story;
    private Intent kiss;
    private Intent river;
    private Intent truth;
    private Intent summer;

    private SharedPreferences sharedPreferences;

    private String song;
    private String backg;
    private String brushType;

    private Drawable drawable = null;
    private Uri imageUri = null;

    private ImageView imageView;

    private Sensor sensor;
    private SensorManager sensorManager;

    private DBHelper dbHelper;

    private NotificationManager notificationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);


        handWrite = findViewById(R.id.handwrite);
        brush = findViewById(R.id.button_brush);
        color = findViewById(R.id.button_color);
        eraser = findViewById(R.id.button_eraser);
        background = findViewById(R.id.background);
        mute = findViewById(R.id.button_mute);
        music = findViewById(R.id.music);
        imageView = findViewById(R.id.temperature);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(BlankActivity.this,sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //music
        canon = new Intent(this, MusicCanon.class);
        dream = new Intent(this, MusicDream.class);
        story = new Intent(this, MusicStory.class);
        kiss = new Intent(this, MusicKiss.class);
        river = new Intent(this, MusicRiver.class);
        truth = new Intent(this, MusicTruth.class);
        summer = new Intent(this, MusicSummer.class);

        dbHelper = new DBHelper(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = getIntent();
        backg = intent.getStringExtra("background");




        if (backg.equalsIgnoreCase("pika")){
            background.setBackgroundResource(R.drawable.pika);
            drawable = ContextCompat.getDrawable(this, R.drawable.pika);
        }else if (backg.equalsIgnoreCase("dog")){
            background.setBackgroundResource(R.drawable.dog);
            drawable = ContextCompat.getDrawable(this, R.drawable.dog);
        }else if (backg.equalsIgnoreCase("owl")){
            background.setBackgroundResource(R.drawable.owl);
            drawable = ContextCompat.getDrawable(this, R.drawable.owl);
        }else if (backg.equalsIgnoreCase("snail")){
            background.setBackgroundResource(R.drawable.snail);
            drawable = ContextCompat.getDrawable(this, R.drawable.snail);
        }else if (backg.equalsIgnoreCase("tree")){
            background.setBackgroundResource(R.drawable.tree);
            drawable = ContextCompat.getDrawable(this, R.drawable.tree);
        }else if (backg.equalsIgnoreCase("car")){
            background.setBackgroundResource(R.drawable.car);
            drawable = ContextCompat.getDrawable(this, R.drawable.car);
        }else if (backg.equalsIgnoreCase("white")){
            background.setBackgroundResource(R.drawable.blank);
            drawable = ContextCompat.getDrawable(this, R.drawable.blank);
        } else if (backg.equalsIgnoreCase("photo")){
            String image = intent.getStringExtra("photo");
            imageUri = Uri.parse(image);
            try {
                drawable = Drawable.createFromStream(getContentResolver().openInputStream(imageUri),"src");//https://blog.csdn.net/gongzhiyao3739124/article/details/51584607
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            background.setBackground(drawable);
        }else if (backg.equalsIgnoreCase("camera")){
            String image = intent.getStringExtra("camera");
            imageUri = Uri.parse(image);
            try {
                drawable = Drawable.createFromStream(getContentResolver().openInputStream(imageUri),"src");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            background.setBackground(drawable);
        }

        Cursor cursor = dbHelper.getData(backg);
        if (cursor.getCount()!=0){
            Toast.makeText(this,"continue",Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()){
                byte[] image = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                drawable = bitmapToDrawable(bitmap,this);
                background.setBackground(drawable);
            }
        }

        handWrite.paint.setXfermode(null);
        handWrite.paint.setStrokeWidth(2.0f);
        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrushDialog();
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handWrite.clear();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMusicDialog();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                handWrite.paint.setColor(temp);
                paintColor=temp;
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Do you want to save image?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //https://www.jianshu.com/p/3526bc69f287
                        //https://blog.csdn.net/qq_33330887/article/details/83652409
                        handWrite.setDrawingCacheEnabled(true);
                        String imageSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(),merge(handWrite.getDrawingCache(true), drawable), UUID.randomUUID()+".jpg","painting");
                        //https://blog.csdn.net/u013749540/article/details/68483335
                        if (imageSaved!=null){
                            Toast.makeText(getApplicationContext(),"Image saved",Toast.LENGTH_SHORT).show();
                            sendNotification();
                        }else{

                        }
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
                return true;

            case R.id.social:
                handWrite.setDrawingCacheEnabled(true);
                Bitmap bitmap = merge(handWrite.getDrawingCache(),drawable);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
//
//                try{
//                    File file = new File(this.getExternalCacheDir(),"image.jpg");
//                    FileOutputStream outputStream = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//                    outputStream.flush();
//                    outputStream.close();
////                    file.setReadable(true,false);
//                    Intent intent1 = new Intent(Intent.ACTION_SEND);
//                    intent1.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
//                    intent.setType("image/*");
//                    startActivity(Intent.createChooser(intent1,"share"));
//                }catch (IOException e){
//
//                }

                //https://blog.csdn.net/yangguannan/article/details/83901187
                //https://blog.csdn.net/songpaul0135/article/details/83657913
                try{
                    String path = Environment.getExternalStorageDirectory()+"/"+"Image.jpg";
                    System.out.println("!!!!!!!!!!!!!!");
                    System.out.println(path);
                    File file = new File(path);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();//
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    System.out.println(file.getPath());
                    System.out.println("^^^^^^^^^^^^");
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(stream.toByteArray());
                    System.out.println(fileOutputStream.toString());
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    startActivity(Intent.createChooser(intent,"Share paint"));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendNotification() {
        String title = "Save successfully";
        String message = "Your painting have saved successfully";
        Intent intent = new Intent(Intent.ACTION_PICK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_photo_library)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setUsesChronometer(true)
                .build();
        notificationManager.notify(1,notification);
    }

    //https://blog.csdn.net/brian512/article/details/50562193?utm_source=blogxgwz3
    private Bitmap merge(Bitmap firstBitmap, Drawable drawable){
        Bitmap secondBitmap;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        secondBitmap = bitmapDrawable.getBitmap();

        int bgWidth = secondBitmap.getWidth();
        int bgHeight = secondBitmap.getHeight();
        int fgWidth = firstBitmap.getWidth();
        int fgHeight = firstBitmap.getHeight();
        System.out.println(bgWidth+"$$$"+bgHeight+"$$$"+fgWidth+"$$$"+fgHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
//        canvas.drawBitmap(firstBitmap, new Rect(0,0,bgWidth/100,bgHeight/100), new Rect(0,0,bgWidth,bgHeight), null);
//        canvas.drawBitmap(secondBitmap, new Rect(0,0,bgWidth,bgHeight),new Rect(0,0,background.getWidth(),background.getHeight()), paint);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        canvas.drawBitmap(firstBitmap, (bgWidth - fgWidth) / 2,(bgHeight - fgHeight) / 2, null);

        canvas.save();
        canvas.restore();
        return newBitmap;
    }

    private void openColorPicker(){
        ColorPickerDialog dialog = new ColorPickerDialog();
        dialog.show(getSupportFragmentManager(),"color picker");
    }

    private void openBrushDialog(){
        BrushDialog dialog = new BrushDialog();
        dialog.show(getSupportFragmentManager(),"brush size");
    }

    private void openMusicDialog(){
        MusicDialog dialog = new MusicDialog();
        dialog.show(getSupportFragmentManager(),"music");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void applyColor(int color) {
        handWrite.paint.setXfermode(null);
        handWrite.paint.setColor(color);
        handWrite.paint.setStrokeWidth(brushSize);
        paintColor=color;
    }

    public void blankMuteVolume(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        if(checked){
            stopService(canon);
            stopService(dream);
            stopService(story);
            stopService(kiss);
            stopService(river);
            stopService(truth);
            stopService(summer);
        }else{
            sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
            song = sharedPreferences.getString("song","");
            if (song.equalsIgnoreCase("canon")){
                startService(canon);
            }else if (song.equalsIgnoreCase("dream")){
                startService(dream);
            }else if (song.equalsIgnoreCase("story")){
                startService(story);
            }else if (song.equalsIgnoreCase("kiss")){
                startService(kiss);
            }else if (song.equalsIgnoreCase("river")){
                startService(river);
            }else if (song.equalsIgnoreCase("truth")){
                startService(truth);
            }else if (song.equalsIgnoreCase("summer")){
                startService(summer);
            }
        }
    }

    @Override
    public void applySize(int size) {
        handWrite.paint.setXfermode(null);
        handWrite.paint.setStrokeWidth(size);
        handWrite.paint.setColor(paintColor);
        brushSize=size;
    }

    @Override
    public void applyType(String type) {
        handWrite.applyType(type);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
//            System.out.println(sensorEvent.values[0]);
            float tem = sensorEvent.values[0];
            if (tem<3){
                temp = Color.rgb(0,12,55);
                imageView.setBackgroundColor(temp);
            }else if (tem<6){
                temp = Color.rgb(26,34,95);
                imageView.setBackgroundColor(temp);
            }else if (tem<9){
                temp = Color.rgb(40,60,139);
                imageView.setBackgroundColor(temp);
            }else if (tem<12){
                temp = Color.rgb(26,96,167);
                imageView.setBackgroundColor(temp);
            }else if (tem<15){
                temp = Color.rgb(35,146,183);
                imageView.setBackgroundColor(temp);
            }else if (tem<18){
                temp = Color.rgb(38,157,154);
                imageView.setBackgroundColor(temp);
            }else if (tem<21){
                temp = Color.rgb(111,159,81);
                imageView.setBackgroundColor(temp);
            }else if (tem<24){
                temp = Color.rgb(157,170,51);
                imageView.setBackgroundColor(temp);
            }else if (tem<27){
                temp = Color.rgb(213,196,50);
                imageView.setBackgroundColor(temp);
            }else if (tem<30){
                temp = Color.rgb(212,160,36);
                imageView.setBackgroundColor(temp);
            }else if (tem<33){
                temp = Color.rgb(233,111,11);
                imageView.setBackgroundColor(temp);
            }else if (tem<36){
                temp = Color.rgb(190,62,18);
                imageView.setBackgroundColor(temp);
            }else{
                temp = Color.rgb(132,21,13);
                imageView.setBackgroundColor(temp);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            handWrite.getCanvas().save();
//            this.finish();
//        }
//        return true;
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            handWrite.setDrawingCacheEnabled(true);
            Bitmap bitmap = merge(handWrite.getDrawingCache(true), drawable);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            Cursor cursor = dbHelper.getData(backg);
            if (cursor.getCount()!=0){
                dbHelper.delete(backg);
            }
            boolean result = dbHelper.insertData(backg,byteArray);
            if (result){
                System.out.println("@@@@@@@@");
            }
        }
        this.finish();
        return true;
    }

    public Drawable bitmapToDrawable(Bitmap bitmap, Context context) {
        BitmapDrawable d = new BitmapDrawable(context.getResources(), bitmap);
        return d;
    }
}
