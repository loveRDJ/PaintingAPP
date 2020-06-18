package com.example.painting.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

//https://www.jianshu.com/p/07f815a54c7e

public class HandWrite extends View {
    
    private int width;
    private int height;
    private float startX, startY=0;
    private Path path;
    public Paint paint;
    private Bitmap bitmap; 
    private Canvas canvas;
    private String brush ="type1";
    

    public HandWrite(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //https://www.youtube.com/watch?v=uJGcmGXaQ0o
    //https://blog.csdn.net/yu540135101/article/details/84928544
    private void init() {
        width = getContext().getResources().getDisplayMetrics().widthPixels;
        height = getContext().getResources().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);//8 bits for alpha,r,g,b
        canvas = new Canvas();
        path = new Path();
        canvas.setBitmap(bitmap);
        paint = new Paint(Paint.DITHER_FLAG);//avoid dithering
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1.0f);
        paint.setStrokeJoin(Paint.Join.ROUND);//when turning
        paint.setStyle(Paint.Style.STROKE);//edge
        paint.setAntiAlias(true);//smooth
        paint.setDither(true);


    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#00FFFFFF")); //background color
//        canvas.drawColor(R.drawable.car);

        Paint paint1 = new Paint();
        canvas.drawBitmap(bitmap,0,0,paint1);
        canvas.drawPath(path,paint);
        canvas.save();
        canvas.restore();
    }

    public void clear(){
        paint.setAlpha(0);
        //https://www.cnblogs.com/SusieBlog/p/5765832.html  106
        //https://www.cnblogs.com/tianzhijiexian/p/4297172.html    4.1
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStrokeWidth(10.0f);
        paint.setColor(Color.WHITE);
    }


    public void applyType(String type){
        brush = type;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                startX=x;
                startY=y;
                long time = event.getEventTime();
                System.out.println(time);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(startX-x);
                float dy = Math.abs(startY-y);
                if (dx>=1||dy>=1){

//                    if (time>3){
//                    }else {
                    if (brush.equalsIgnoreCase("type1")) {
                        path.quadTo(startX, startY, x, y);
                    }else if (brush.equalsIgnoreCase("type2")){
                            path.moveTo(startX,startY);
                            path.lineTo(x,y);
                    }else if (brush.equalsIgnoreCase("type3")){
                            path.moveTo(startX,startY);
                            path.arcTo(new RectF(startX,startY,x,y),0,90);
                    }
//                    }

                    startX=x;
                    startY=y;
                }
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path,paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
