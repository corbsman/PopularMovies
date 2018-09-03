package com.popularmovies.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

    public class MovieCollection implements Parcelable {

        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("total_results")
        @Expose
        private Integer totalResults;
        @SerializedName("total_pages")
        @Expose
        private Integer totalPages;
        @SerializedName("results")
        @Expose
        private List<Movie> results = null;

        protected MovieCollection(Parcel in) {
            if (in.readByte() == 0) {
                page = null;
            } else {
                page = in.readInt();
            }
            if (in.readByte() == 0) {
                totalResults = null;
            } else {
                totalResults = in.readInt();
            }
            if (in.readByte() == 0) {
                totalPages = null;
            } else {
                totalPages = in.readInt();
            }
            List tempList = new ArrayList<Movie>();
            in.readList(tempList, Movie.class.getClassLoader());
            if (tempList == null) {
                results = new ArrayList<Movie>();
            } else {
                results = tempList;
            }


        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (page == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(page);
            }
            if (totalResults == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(totalResults);
            }
            if (totalPages == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(totalPages);
            }
            if (results == null) {
                //figure out if we need something here
            } else {
                dest.writeList(results);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MovieCollection> CREATOR = new Creator<MovieCollection>() {
            @Override
            public MovieCollection createFromParcel(Parcel in) {
                return new MovieCollection(in);
            }

            @Override
            public MovieCollection[] newArray(int size) {
                return new MovieCollection[size];
            }
        };

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public List<Movie> getResults() {
            return results;
        }

        public void setResults(List<Movie> results) {
            this.results = results;
        }

    }


