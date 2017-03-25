package com.tutorial.exercise.ImageSearchView;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorial.exercise.Model.SearchImageModel;
import com.tutorial.exercise.R;
import com.tutorial.exercise.Utils.Provider;

import java.util.ArrayList;
import java.util.List;


public class SearchViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchImageModel> mDataset = new ArrayList<>();
    private Context ctx;

    public SearchViewAdapter(Context ctx, List<SearchImageModel> imageModelList) {
        this.ctx = ctx;
        mDataset = new ArrayList<>(imageModelList);
        Provider.getInstance().register(this);
    }

    public void addItem(int position, SearchImageModel item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position)
    {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView imageTxt;
        SearchAlertDialog searchAlertDialog;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageTxt = (TextView) itemView.findViewById(R.id.imageTxt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_layout, parent, false);

        SearchViewAdapter.ViewHolder pavh = new SearchViewAdapter.ViewHolder(itemLayoutView);

        return pavh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder vh = (ViewHolder) holder;

        Picasso.with(ctx)
                .load(mDataset.get(position).getPreviewURL())
                .placeholder(R.drawable.placeholder) //this is optional the image to display while the url image is downloading
                .into(vh.imageView);

        vh.imageTxt.setText(mDataset.get(position).getTags());
        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchImageModel searchImageModel = new SearchImageModel();
                searchImageModel.setWebformatURL(mDataset.get(position).getWebformatURL());
                Provider.getInstance().post(searchImageModel);

                /*if (vh.searchAlertDialog == null) {
                    LayoutInflater li = LayoutInflater.from(ctx);
                    vh.searchAlertDialog = new SearchAlertDialog(new AlertDialog.Builder(ctx).create(), li,mDataset.get(position).getWebformatURL(),ctx);
                    vh.searchAlertDialog.show();
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
