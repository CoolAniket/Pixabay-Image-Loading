package com.tutorial.exercise.ImageSearchView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tutorial.exercise.Handler.HttpHandler;
import com.tutorial.exercise.MainActivity;
import com.tutorial.exercise.Model.SearchImageModel;
import com.tutorial.exercise.Utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aniketrao Rane on 24-03-2017.
 */

public class ImageRV extends RecyclerView
{
    private LinearLayoutManager manager;
    private SearchViewAdapter searchViewAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<SearchImageModel> imageModelList;

    public ImageRV(Context context) {
        super(context);
        init();
    }

    public ImageRV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageRV(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        this.manager = new LinearLayoutManager(getContext());
        this.manager.setOrientation(LinearLayoutManager.VERTICAL);
        this.setLayoutManager(this.manager);
        this.setHasFixedSize(true);
    }

    public void readImages(String Link)
    {
        new GetImages().execute(Link);
    }

    private class GetImages extends AsyncTask<String, String, List<SearchImageModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected List<SearchImageModel> doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(params[0]);
            imageModelList = new ArrayList<>();
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray hitsArray = jsonObj.getJSONArray("hits");

                    // looping through All Hits
                    for (int i = 0; i < hitsArray.length(); i++)
                    {
                        JSONObject hitsObject = hitsArray.getJSONObject(i);
                        SearchImageModel searchImageModel = new SearchImageModel();
                        if (hitsObject.has("previewURL")) {
                            searchImageModel.setPreviewURL(hitsObject.getString("previewURL"));}

                        if (hitsObject.has("webformatURL")) {
                            searchImageModel.setWebformatURL(hitsObject.getString("webformatURL"));
                        }

                        if (hitsObject.has("tags")) {
                            searchImageModel.setTags(hitsObject.getString("tags"));
                        }
                        //---for adding new search item----//
                        //searchViewAdapter.addItem(searchViewAdapter.getItemCount(),searchImageModel);
                        imageModelList.add(searchImageModel);
                    }
                    return imageModelList;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SearchImageModel> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into recycleView
             * */
            searchViewAdapter = new SearchViewAdapter(getContext(),result);
            setAdapter(searchViewAdapter);
        }
    }

}
