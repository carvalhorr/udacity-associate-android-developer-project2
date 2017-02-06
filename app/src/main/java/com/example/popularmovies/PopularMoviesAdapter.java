package com.example.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carvalhorr on 1/18/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewholder> {

    private List<MovieInfo> mMovieInfoData;
    private final MovieOnClickHandler mMovieOnClickHandler;
    private boolean mFavorites;

    public PopularMoviesAdapter(MovieOnClickHandler movieOnClickHandler, boolean favorites) {
        this.mMovieOnClickHandler = movieOnClickHandler;
        this.mFavorites = favorites;

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
                if (mFavorites) {
                    mMovieOnClickHandler.onClick(mMovieInfo.getMovieId());
                } else {
                    mMovieOnClickHandler.onClick(mMovieInfo);
                }
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
        void onClick(String movieId);
    }
}
