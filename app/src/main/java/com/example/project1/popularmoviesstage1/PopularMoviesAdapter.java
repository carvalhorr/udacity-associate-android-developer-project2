package com.example.project1.popularmoviesstage1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.project1.popularmoviesstage1.model.MovieInfo;
import com.example.project1.popularmoviesstage1.network.PopularMoviesAPI;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by carvalhorr on 1/18/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewholder> {

    private List<MovieInfo> mMovieInfoData;
    private final MovieOnClickHandler mMovieOnClickHandler;

    public PopularMoviesAdapter(MovieOnClickHandler movieOnClickHandler) {
        this.mMovieOnClickHandler = movieOnClickHandler;

    }

    @Override
    public PopularMoviesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToRoot = false;

        View view = layoutInflater.inflate(R.layout.movie_grid_item, parent, attachToRoot);

        PopularMoviesViewholder popularMoviesViewholder = new PopularMoviesViewholder(view);

        return popularMoviesViewholder;
    }

    @Override
    public void onBindViewHolder(PopularMoviesViewholder holder, int position) {
        MovieInfo movieInfo = mMovieInfoData.get(position);
        holder.bind(movieInfo);
    }

    @Override
    public int getItemCount() {
        if (mMovieInfoData == null) return 0;
        return mMovieInfoData.size();
    }

    public void setMovieInfoData(List<MovieInfo> movieInfoData) {
        this.mMovieInfoData = movieInfoData;
        notifyDataSetChanged();
    }

    public class PopularMoviesViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private MovieInfo mMovieInfo;
        private ImageView mMovieThumbnailImageView;

        public PopularMoviesViewholder(View itemView) {
            super(itemView);
            mMovieThumbnailImageView =
                    (ImageView) itemView.findViewById(R.id.iv_movie_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mMovieOnClickHandler != null) {
                mMovieOnClickHandler.onClick(mMovieInfo);
            }
        }

        public void bind(MovieInfo movieInfo) {
            mMovieInfo = movieInfo;
            Picasso.with(mMovieThumbnailImageView.getContext())
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getPosterPath())
                    .into(mMovieThumbnailImageView);
        }
    }



    public interface MovieOnClickHandler {
        void onClick(MovieInfo movieInfo);
    }
}
