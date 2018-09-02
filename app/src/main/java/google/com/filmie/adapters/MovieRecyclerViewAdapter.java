package google.com.filmie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import google.com.filmie.R;
import google.com.filmie.constants.MovieConstants;
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
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
