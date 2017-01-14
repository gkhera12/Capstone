package com.example.eightleaves.comedybox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eightleaves.comedybox.R;
import com.example.eightleaves.comedybox.data.models.Trailer;
import com.example.eightleaves.comedybox.events.PlayTrailerEvent;
import com.example.eightleaves.comedybox.otto.ComedyBus;

import java.util.List;

/**
 * Created by gkhera on 31/12/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private final Context mContext;
    private List<Trailer> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.list_item_trailer_image_icon);
            mTextView = (TextView) view.findViewById(R.id.list_item_trailer_number);
        }

    }

    public TrailerAdapter(Context context, List<Trailer> items) {
        mValues = items;
        mContext = context;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View trailerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trailer, parent, false);
        return new ViewHolder(trailerView);
    }

    public Trailer getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(mValues.get(position).getName());
        holder.mView.setContentDescription(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayTrailerEvent event = new PlayTrailerEvent();
                event.setTitle(mValues.get(position).getName());
                event.setUrl(mValues.get(position).getSite());
                event.setPosition(position);
                ComedyBus.getInstance().post(event);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mValues != null){
        return mValues.size();
        }else{
            return 0;
        }
    }
}
