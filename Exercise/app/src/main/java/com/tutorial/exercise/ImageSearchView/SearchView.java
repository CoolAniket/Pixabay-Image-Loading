package com.tutorial.exercise.ImageSearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.tutorial.exercise.FullImage;
import com.tutorial.exercise.Handler.NetworkHandler;
import com.tutorial.exercise.Model.SearchImageModel;
import com.tutorial.exercise.R;
import com.tutorial.exercise.Utils.AppConstant;
import com.tutorial.exercise.Utils.Provider;

/**
 * Created by Aniketrao Rane on 24-03-2017.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {
    private LayoutInflater mInflater;
    private View view;
    private EditText searchTxt;
    private Button searchBtn;
    private String getSearchStr;
    private ImageRV imageRV;
    private SearchAlertDialog searchAlertDialog;
    private InputMethodManager inputManager;
    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init()
    {
        //----Initializing Bus Method---//
        Provider.getInstance().register(this);

        mInflater = LayoutInflater.from(getContext());

        view = mInflater.inflate(R.layout.searchview_layout,this,true);

        imageRV = (ImageRV)view.findViewById(R.id.imageRV);

        searchTxt = (EditText)view.findViewById(R.id.searchTxt);
        searchTxt.requestFocus();
        inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        searchBtn = (Button)view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
    }

    @Subscribe
    public void getProgData(SearchImageModel data) {
        Log.d("Bus update ", "Subscribe here--->display image");

        //----for showing image in dialog box ---//
        /*if (searchAlertDialog == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            searchAlertDialog = new SearchAlertDialog(new AlertDialog.Builder(getContext()).create(), li,data.getWebformatURL(),getContext());
            searchAlertDialog.show();
        }*/

        Intent intent = new Intent(getContext(), FullImage.class);
        intent.putExtra("fullimageurl",data.getWebformatURL());
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBtn:
                if(NetworkHandler.getInstance(getContext()).isNetworkAvailable()) {
                    getSearchStr = searchTxt.getText().toString().toLowerCase();
                    imageRV.readImages(AppConstant.IMAGE_URL + getSearchStr);
                    inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    searchTxt.setText("");
                }
                else
                {
                    Toast.makeText(getContext(),
                            "Internet is not available, Please Connect to your internet.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                break;
        }
    }
}
