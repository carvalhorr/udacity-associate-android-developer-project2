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
 * Created by carvalhorr on 1/18/17.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewholder> {

    private List<MovieVideo> mMovieVideos;
    private final VideoOnClickHandler mVideoOnClickHandler;

    public MovieVideosAdapter(VideoOnClickHandler videoOnClickHandler) {
        this.mVideoOnClickHandler = videoOnClickHandler;
    }

    @Override
    public MovieVideosViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToRoot = false;

        View view = layoutInflater.inflate(R.layout.movie_card, parent, attachToRoot);

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

    public void setMovieInfoData(List<MovieVideo> movieVideos) {
        if (this.mMovieVideos != null) {
            this.mMovieVideos.clear();
            this.mMovieVideos.addAll(movieVideos);
        } else {
            this.mMovieVideos = movieVideos;
        }
        notifyDataSetChanged();
    }

    public class MovieVideosViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private MovieVideo mMovieVideo;
        private TextView mNameTextView;

        public MovieVideosViewholder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mVideoOnClickHandler != null) {
                mVideoOnClickHandler.onClick(mMovieVideo);
            }
        }

        public void bind(MovieVideo movieVideo) {
            mMovieVideo = movieVideo;
            mNameTextView.setText(mMovieVideo.getName());
        }
    }

    public interface VideoOnClickHandler {
        void onClick(MovieVideo movieVideo);
    }
}
