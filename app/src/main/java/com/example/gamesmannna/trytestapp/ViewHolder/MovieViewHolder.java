package com.example.gamesmannna.trytestapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamesmannna.trytestapp.Interface.ItemClickListener;
import com.example.gamesmannna.trytestapp.R;

/**
 * Created by Gamesmannna on 04.06.2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView movie_name;
    public ImageView movie_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MovieViewHolder(View itemView) {
        super(itemView);

        movie_name = (TextView) itemView.findViewById(R.id.movie_name);
        movie_image = (ImageView) itemView.findViewById(R.id.movie_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }
}
