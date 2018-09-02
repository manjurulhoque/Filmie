package google.com.filmie.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import google.com.filmie.R;
import google.com.filmie.adapters.CustomSpinnerAdapter;
import google.com.filmie.adapters.MovieRecyclerViewAdapter;
import google.com.filmie.constants.ApiKey;
import google.com.filmie.constants.MovieConstants;
import google.com.filmie.models.Movie;
import google.com.filmie.models.MovieList;
import google.com.filmie.networks.MovieService;
import google.com.filmie.networks.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String sortBy;
    int page = 1;
    private List<Movie> movies = new ArrayList<Movie>();
    private ProgressDialog mProgressDialog;

    RecyclerView movieRecyclerView;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.fragment_home_framelayout)
    FrameLayout homeFragmentFrameLayout;

    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    View view;

    int navColor;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        movieRecyclerView = view.findViewById(R.id.recyclerViewMovies);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        sortBy = MovieConstants.SORT_BY_POPULARITY;
        page = 1;

        getMovies();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int gridColumns = getResources().getInteger(R.integer.grid_columns);
        navColor = ContextCompat.getColor(getActivity(), (R.color.colorPrimaryTransparentNav));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setProgressViewOffset(true, 200, 500);
        mSwipeRefreshLayout.setEnabled(false);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), gridColumns);

        movieRecyclerView.setLayoutManager(mGridLayoutManager);
        //movieRecyclerView.setHasFixedSize(true);
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies, navColor, getActivity());
        movieRecyclerView.setAdapter(movieRecyclerViewAdapter);

        Spinner spinner = getActivity().findViewById(R.id.spinnerToolbar);
        final CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getActivity());
        String[] sortOptions = getResources().getStringArray(R.array.sort_by);
        spinnerAdapter.addItems(Arrays.asList(sortOptions));
        spinner.setAdapter(spinnerAdapter);


        //currentSortingBy = MovieConstants.SORT_BY_POPULARITY;
        //PreferenceManager.getInstance(getActivity()).writeValue(MovieConstants.SORT_BY, currentSortingBy);

        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(this);
    }

    private void getMovies() {
        movies.clear();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.show();

        MovieService service = RetrofitBuilder.buildRetrofit().create(MovieService.class);

        Call<MovieList> call = service.getMovieList(ApiKey.KEY, sortBy, page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                mProgressDialog.dismiss();
                if (response.code() == 200) {
                    MovieList movieList = response.body();
                    for (int i = 0; i < movieList.getResults().size(); i++) {
                        movies.add(movieList.getResults().get(i));
                    }
                    movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies, navColor, getActivity());
                    movieRecyclerView.setAdapter(movieRecyclerViewAdapter);
                    movieRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String category = adapterView.getItemAtPosition(i).toString();
        switch (i) {
            case 0:
                sortBy = MovieConstants.SORT_BY_POPULARITY;
                break;
            case 1:
                sortBy = MovieConstants.SORT_BY_RATINGS;
                break;
            case 2:
                sortBy = MovieConstants.SORT_BY_FAVOURITES;
                break;
            default:
                sortBy = MovieConstants.SORT_BY_POPULARITY;
                break;
        }
        getMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
