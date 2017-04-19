package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Information about a movie that is necessary for the popular movies app. Note that
 * the movie db api provides extra info that is not included here.
 *
 * Created by carvalhorr on 1/18/17.
 */

public class MovieInfo implements Parcelable {

    // Movie id
    @SerializedName("id")
    private String movieId;

    // Movie title
    @SerializedName("title")
    private String title;

    // Name of the file for the movie poster
    @SerializedName("poster_path")
    private String posterPath;

    // Name of the backdrop image for the movie
    @SerializedName("backdrop_path")
    private String backdropPath;

    // Movie release date
    @SerializedName("release_date")
    private Date releaseDate;

    // Vote average for the movie
    @SerializedName("vote_average")
    private Double voteAverage;

    // Movie plot
    @SerializedName("overview")
    private String mPlot;

    public MovieInfo() {
    }

    protected MovieInfo(Parcel in) {
        movieId = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        mPlot = in.readString();
        releaseDate = new Date(in.readLong());
        voteAverage = in.readDouble();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String mMovieId) {
        this.movieId = mMovieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.posterPath = mPosterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date mReleaseDate) {
        this.releaseDate = mReleaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double mVoteAverage) {
        this.voteAverage = mVoteAverage;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(mPlot);
        dest.writeLong(releaseDate.getTime());
        dest.writeDouble(voteAverage);
    }
}
