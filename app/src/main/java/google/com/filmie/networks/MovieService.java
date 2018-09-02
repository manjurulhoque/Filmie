package google.com.filmie.networks;

import google.com.filmie.constants.MovieConstants;
import google.com.filmie.models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET(MovieConstants.GET_MOVIES_URL)
    Call<MovieList> getMovieList(@Query(MovieConstants.API_KEY) String apiKey,
                                 @Query(MovieConstants.SORT_BY) String sortBy, @Query(MovieConstants.PAGE) int page);
}