package google.com.filmie.constants;

public class MovieConstants {

    // base urls
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    public final static String IMAGE_SIZE_SMALL = "w185/";
    public final static String IMAGE_SIZE_LARGE = "w500/";

    // end points urls
    public static final String GET_MOVIES_URL = "/3/discover/movie";
    public static final String GET_REVIEWS_URL = "/3/movie/{id}/reviews";
    public static final String GET_TRAILERS_URL = "/3/movie/{id}/videos";

    // api parameters
    public static final String API_KEY = "api_key";
    public static final String SORT_BY = "sort_by";
    public static final String PAGE = "page";
    public static final String ID = "id";

    // sorting
    public static final String SORT_BY_POPULARITY = "popularity.desc";
    public static final String SORT_BY_RATINGS = "vote_average.desc";
    public static final String SORT_BY_FAVOURITES = "show_favourites";
}
