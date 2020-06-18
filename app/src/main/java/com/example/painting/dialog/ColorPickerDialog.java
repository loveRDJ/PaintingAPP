package com.example.painting.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.painting.R;


public class ColorPickerDialog extends AppCompatDialogFragment {

    private ImageView colorPicker;
    private ImageView showColor;

    private Bitmap bitmap;

    private ColorPickerDialogListener listener;

    private int color=Color.BLACK;
    private int pixel=0;

    private int r,g,b;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_colorpicker,null);

        colorPicker = view.findViewById(R.id.color_picker);
        showColor = view.findViewById(R.id.color);


        colorPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.color_picker);
                    System.out.println(motionEvent.getX()+"!!!!!!!!!!!!!!"+motionEvent.getY());
                    System.out.println(bitmap.getWidth()+"$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println(bitmap.getHeight()+"$$$$$$$$$$$$$$$$$$$$$$$$$");
                    pixel = bitmap.getPixel((int)motionEvent.getX(),(int)motionEvent.getY());
                    System.out.println(pixel+"??????????");

                    r = Color.red(pixel);
                    g = Color.green(pixel);
                    b = Color.blue(pixel);

//                    color = Color.argb(1,r,g,b);
                    color = Color.rgb(r,g,b);

                    showColor.setBackgroundColor(color);
                }
                return true;
            }
        });

        builder.setView(view)
                .setTitle("Pick a color")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyColor(color);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ColorPickerDialogListener) context;
    }

    public interface ColorPickerDialogListener{
        void applyColor(int color);
    }
}

