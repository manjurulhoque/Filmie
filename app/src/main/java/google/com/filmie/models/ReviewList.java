package google.com.filmie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {

    @SerializedName("results")
    public List<Review> results;
    @SerializedName("page")
    public int page;

    public ReviewList() {
    }

    public ReviewList(List<Review> results, int page) {
        this.results = results;
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
