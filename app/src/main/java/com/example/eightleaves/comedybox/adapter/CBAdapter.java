package com.example.eightleaves.comedybox.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.eightleaves.comedybox.R;
import com.squareup.picasso.Picasso;

/**
 * Created by gkhera on 24/12/2016.
 */

public class CBAdapter extends CursorAdapter {

    public CBAdapter(Context context, Cursor cursor) {
        super(context,cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cb, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        String imageUrl = "http://media.radiomirchi.com/audios/audio_content/thumbnail_1430304758.jpg";
        Picasso.with(context).load(imageUrl)
                .into(viewHolder.imageView);
    }

    public static class ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.list_item_cb_image);
        }
    }
}
