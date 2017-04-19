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
 * Adapter for displaying the list of reviews for a movie
 * <p>
 * Created by carvalhorr on 1/18/17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewholder> {

    // Holds the list of reviews
    private List<MovieReview> movieReviews;

    // Reference to the external handler for a review click
    private final ReviewOnClickHandler reviewOnClickHandler;

    public MovieReviewsAdapter(ReviewOnClickHandler reviewOnClickHandler) {

        // Stores the reference to the handler of a review click
        this.reviewOnClickHandler = reviewOnClickHandler;

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

        MovieReview movieReview = movieReviews.get(position);
        holder.bind(movieReview);

    }

    @Override
    public int getItemCount() {

        if (movieReviews == null) return 0;
        return movieReviews.size();

    }

    /**
     * Used by external loader to pass list of reviews to the Adapter when load is finished
     *
     * @param movieReviews
     */
    public void setMovieReviews(List<MovieReview> movieReviews) {

        this.movieReviews = movieReviews;
        notifyDataSetChanged();

    }

    public class MovieReviewsViewholder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Declare variables for the view elements
        private MovieReview movieReview;
        private TextView author;
        private TextView content;

        public MovieReviewsViewholder(View itemView) {

            super(itemView);

            // Set the click handler for the review
            itemView.setOnClickListener(this);

            // Get references to view elements
            author = (TextView) itemView.findViewById(R.id.tv_author);
            content = (TextView) itemView.findViewById(R.id.tv_content);

        }

        /**
         * Call external click handler when a review is clicked if it was provided
         *
         * @param v
         */
        @Override
        public void onClick(View v) {

            if (reviewOnClickHandler != null) {

                reviewOnClickHandler.onClick(movieReview);

            }

        }

        /**
         * Store and display the review info on the user interface
         */
        public void bind(MovieReview movieReview) {
            this.movieReview = movieReview;
            author.setText(this.movieReview.getAuthor());
            content.setText(this.movieReview.getContent());
        }
    }

    /**
     * Interface that external components needs to implement to handle a review click
     */
    public interface ReviewOnClickHandler {
        void onClick(MovieReview movieReview);
    }
}
