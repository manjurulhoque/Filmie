package google.com.filmie.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import google.com.filmie.MainActivity;
import google.com.filmie.R;
import google.com.filmie.constants.MovieConstants;
import google.com.filmie.fragments.DetailFragment;
import google.com.filmie.models.Movie;
import google.com.filmie.viewholders.MovieViewHolder;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private Context mContext;
    int defaultColor;

    public MovieRecyclerViewAdapter(List<Movie> movieList, int defaultColor, Context context) {
        this.movieList = movieList;
        this.defaultColor = defaultColor;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_row, parent, false);

        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        String imageUrl = MovieConstants.IMAGE_BASE_URL + MovieConstants.IMAGE_SIZE_LARGE +
                movie.getPosterPath();

        holder.textViewTitle.setText(movie.getTitle());

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.place_holder)
                .into(holder.gridMoviePosterImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                fragment.setArguments(bundle);
                loadFragment(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "DetailFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
