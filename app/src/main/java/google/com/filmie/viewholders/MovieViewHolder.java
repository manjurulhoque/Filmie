package google.com.filmie.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import google.com.filmie.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView gridMoviePosterImage;

    public RatingBar ratingBar;

    public TextView textViewTitle;

    public CardView cardView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        gridMoviePosterImage = itemView.findViewById(R.id.grid_item_movie_poster_image);
        ratingBar = itemView.findViewById(R.id.rating);
        cardView = itemView.findViewById(R.id.grid_item_container);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
    }
}
