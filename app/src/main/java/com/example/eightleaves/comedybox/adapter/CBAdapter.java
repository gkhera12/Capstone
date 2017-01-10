package com.example.eightleaves.comedybox.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eightleaves.comedybox.MainFragment;
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
        String imageUrl = cursor.getString(MainFragment.COL_COMEDY_POSTER_PATH);
        String artist = cursor.getString(MainFragment.COL_COMEDY_TITLE);
        Picasso.with(context).load(imageUrl)
                .into(viewHolder.imageView);
        view.setContentDescription(artist);
        viewHolder.title.setText(artist);
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView title;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.list_item_cb_image);
            title = (TextView)view.findViewById(R.id.list_item_cb_text);
        }
    }
}
