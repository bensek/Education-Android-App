package com.example.hp.eduapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.eduapp.R;

/**
 * Created by radman on 10/1/2016.
 */
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    private Context c;

    public PicturesAdapter(Context context) {
        this.c = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View picturesView = inflater.inflate(R.layout.item_pictures, parent, false);
        return new PicturesAdapter.ViewHolder(picturesView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pic.setImageResource(R.drawable.course_icon);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.item_pictures_imageView);
        }
    }
}
