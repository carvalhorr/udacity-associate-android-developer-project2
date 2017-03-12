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
    private String mMovieId;

    // Movie title
    @SerializedName("title")
    private String mTitle;

    // Name of the file for the movie poster
    @SerializedName("poster_path")
    private String mPosterPath;

    // Name of the backdrop image for the movie
    @SerializedName("backdrop_path")
    private String mBackdropPath;

    // Movie release date
    @SerializedName("release_date")
    private Date mReleaseDate;

    // Vote average for the movie
    @SerializedName("vote_average")
    private Double mVoteAverage;

    // Movie plot
    @SerializedName("overview")
    private String mPlot;

    public MovieInfo() {
    }

    protected MovieInfo(Parcel in) {
        mMovieId = in.readString();
        mTitle = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mPlot = in.readString();
        mReleaseDate = new Date(in.readLong());
        mVoteAverage = in.readDouble();
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
        return mMovieId;
    }

    public void setMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
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
        dest.writeString(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
        dest.writeString(mPlot);
        dest.writeLong(mReleaseDate.getTime());
        dest.writeDouble(mVoteAverage);
    }
}
