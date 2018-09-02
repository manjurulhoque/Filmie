package google.com.filmie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieList implements Serializable {

    @SerializedName("results")
    public List<Movie> results;
    @SerializedName("page")
    public int page;

    public MovieList(List<Movie> results, int page) {
        this.results = results;
        this.page = page;
    }

    public MovieList() {
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Page: " + page + "]");
        for (Movie movie : results) {
            sb.append("[Movie:" + movie.getTitle() + "]\n");
        }
        return sb.toString();
    }
}
