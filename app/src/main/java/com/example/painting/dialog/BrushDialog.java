package com.example.painting.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.painting.object.HandWrite;
import com.example.painting.R;

//https://www.youtube.com/watch?v=ARezg1D9Zd0

public class BrushDialog extends AppCompatDialogFragment {

    private HandWrite brushView;

    private SeekBar seekBar;

    private BrushDialogListener listener;

    private Button type1,type2,type3;

    private String type = "type1";

    private int size=1;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_brush,null);

        brushView = view.findViewById(R.id.brush);
        seekBar = view.findViewById(R.id.seek_bar);
        type1 = view.findViewById(R.id.brush_type1);
        type2 = view.findViewById(R.id.brush_type2);
        type3 = view.findViewById(R.id.brush_type3);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brushView.getCanvas().drawPaint(brushView.paint);
                brushView.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                brushView.paint.setStyle(Paint.Style.FILL_AND_STROKE);
                brushView.getCanvas().drawColor(Color.WHITE);
                brushView.getCanvas().drawCircle(brushView.getWidth()/2,brushView.getHeight()/2,i,brushView.paint);//the center of the circle
                size = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "type1";
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "type2";
            }
        });

        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "type3";
            }
        });

        builder.setTitle("Choose the size")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applySize(size);
                        listener.applyType(type);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });







        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (BrushDialogListener) context;
    }

    public interface BrushDialogListener{
        void applySize(int size);

        void applyType(String type);
    }
}
