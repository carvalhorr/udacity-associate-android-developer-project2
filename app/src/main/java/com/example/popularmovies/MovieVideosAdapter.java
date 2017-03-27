package com.example.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.model.MovieVideo;

import java.util.List;

/**
 * Adapter for displaying a list of videos for a movie
 * Created by carvalhorr on 1/18/17.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewholder> {

    // holds the list of videos
    private List<MovieVideo> mMovieVideos;

    // External video click handler
    private final VideoOnClickHandler mVideoOnClickHandler;

    public MovieVideosAdapter(VideoOnClickHandler videoOnClickHandler) {

        // Stores the external video click handler
        this.mVideoOnClickHandler = videoOnClickHandler;

    }

    @Override
    public MovieVideosViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToRoot = false;

        View view = layoutInflater.inflate(R.layout.video_card, parent, attachToRoot);

        MovieVideosViewholder movieVideosViewholder = new MovieVideosViewholder(view);

        return movieVideosViewholder;

    }

    @Override
    public void onBindViewHolder(MovieVideosViewholder holder, int position) {

        MovieVideo movieVideo = mMovieVideos.get(position);
        holder.bind(movieVideo);

    }

    @Override
    public int getItemCount() {

        if (mMovieVideos == null) return 0;
        return mMovieVideos.size();

    }

    /**
     * Used by external loader to pass list of videos to the Adapter when load is finished
     *
     * @param movieVideos
     */
    public void setMovieInfoData(List<MovieVideo> movieVideos) {

        this.mMovieVideos = movieVideos;
        notifyDataSetChanged();

    }

    public class MovieVideosViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Declare variables for view elements
        private MovieVideo mMovieVideo;
        private TextView mNameTextView;

        public MovieVideosViewholder(View itemView) {

            super(itemView);

            // Get reference to the view elements
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_video_title);

            // Setup the review view click handler
            itemView.setOnClickListener(this);

        }

        /**
         * Call external video click handler when a video is clicked
         * @param v
         */
        @Override
        public void onClick(View v) {

            if (mVideoOnClickHandler != null) {
                mVideoOnClickHandler.onClick(mMovieVideo);
            }

        }

        /**
         * Store and display the video info received
         * @param movieVideo
         */
        public void bind(MovieVideo movieVideo) {

            mMovieVideo = movieVideo;
            mNameTextView.setText(mMovieVideo.getName());

        }
    }

    /**
     * Interface that external components need to implement to handle a click on a video
     */
    public interface VideoOnClickHandler {
        void onClick(MovieVideo movieVideo);
    }
}
