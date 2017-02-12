package com.example.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.model.MovieReview;

import java.util.List;

/**
 * Created by carvalhorr on 1/18/17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewholder> {

    private List<MovieReview> mMovieReviews;
    private final ReviewOnClickHandler mVideoOnClickHandler;

    public MovieReviewsAdapter(ReviewOnClickHandler videoOnClickHandler) {
        this.mVideoOnClickHandler = videoOnClickHandler;
    }

    @Override
    public MovieReviewsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToRoot = false;

        View view = layoutInflater.inflate(R.layout.review_card, parent, attachToRoot);

        MovieReviewsViewholder movieReviewsViewholder = new MovieReviewsViewholder(view);

        return movieReviewsViewholder;
    }

    @Override
    public void onBindViewHolder(MovieReviewsViewholder holder, int position) {
        MovieReview movieReview = mMovieReviews.get(position);
        holder.bind(movieReview);
    }

    @Override
    public int getItemCount() {
        if (mMovieReviews == null) return 0;
        return mMovieReviews.size();
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.mMovieReviews = movieReviews;
        notifyDataSetChanged();
    }

    public class MovieReviewsViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private MovieReview mMovieReview;
        private TextView mAuthor;
        private TextView mContent;

        public MovieReviewsViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        public void onClick(View v) {
            if (mVideoOnClickHandler != null) {
                mVideoOnClickHandler.onClick(mMovieReview);
            }
        }

        public void bind(MovieReview movieReview) {
            mMovieReview = movieReview;
            mAuthor.setText(mMovieReview.getAuthor());
            mContent.setText(mMovieReview.getContent());
        }
    }

    public interface ReviewOnClickHandler {
        void onClick(MovieReview movieReview);
    }
}
