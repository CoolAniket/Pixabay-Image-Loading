package com.tutorial.exercise.ImageSearchView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.tutorial.exercise.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aniketrao Rane on 20-03-2017.
 */

public class SearchAlertDialog
{
    private Handler handler;
    private AlertDialog alertDialog;
    private View view;
    private ImageView displayImageView;

    public SearchAlertDialog(@NonNull AlertDialog alertDialog_, LayoutInflater li, String imageurl, Context ctx)
    {
        this.alertDialog = alertDialog_;
        this.handler = new Handler();

        this.view = li.inflate(R.layout.displayimage_layout, null);
        displayImageView = (ImageView)view.findViewById(R.id.displayImage);

        Picasso.with(ctx)
                .load(imageurl)
                .placeholder(R.drawable.placeholder) //this is optional the image to display while the url image is downloading
                .into(displayImageView);

        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(final DialogInterface dialog) {

            }
        });
    }


    public void init()
    {
        this.handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },500);
    }

    public void show()
    {
        alertDialog.show();
    }

    public void dismiss()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(alertDialog.isShowing())
                {
                    alertDialog.dismiss();
                }
            }
        });
    }
}
