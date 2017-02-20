package com.example.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for displaying the list of movies.
 * <p>
 * Created by carvalhorr on 1/18/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.PopularMoviesViewholder> {

    // Holds the list of videos.
    private List<MovieInfo> mMovieInfoData;

    // Holds a reference to an external movie click handler
    private final MovieOnClickHandler mMovieOnClickHandler;

    // Indicate whether the adapter holds a list of favorite movies or not. Each MovieInfo in the
    // list of favorites do not contain all the movie info, therefore a different call is made in
    // the external click handler.
    private boolean mFavorites;

    public MovieListAdapter(MovieOnClickHandler movieOnClickHandler, boolean favorites) {

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

    /**
     * Used by external loader to pass the list of movies when it finished loading.
     *
     * @param movieInfoData
     */
    public void setMovieInfoData(List<MovieInfo> movieInfoData) {

        this.mMovieInfoData = movieInfoData;
        notifyDataSetChanged();

    }

    public class PopularMoviesViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Declare variables for the view elements
        private MovieInfo mMovieInfo;
        private ImageView mMovieThumbnailImageView;

        public PopularMoviesViewholder(View itemView) {
            super(itemView);

            // Get reference to the movie poster image view
            mMovieThumbnailImageView =
                    (ImageView) itemView.findViewById(R.id.iv_movie_thumbnail);

            // set the click handler for the movie item
            itemView.setOnClickListener(this);
        }

        /**
         * Call the corresponding click handler method on the external handler when a movie is
         * clicked.
         *
         * @param v
         */
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

        /**
         * Store and display the movie info.
         *
         * @param movieInfo
         */
        public void bind(MovieInfo movieInfo) {
            mMovieInfo = movieInfo;
            Picasso.with(mMovieThumbnailImageView.getContext())
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getPosterPath())
                    .placeholder(R.drawable.poster)
                    .error(R.drawable.poster)
                    .into(mMovieThumbnailImageView);
        }
    }

    /**
     * Interface that external components need to implement in order to handle a click on a movie.
     */
    public interface MovieOnClickHandler {

        void onClick(MovieInfo movieInfo);

        void onClick(String movieId);
        
    }
}
