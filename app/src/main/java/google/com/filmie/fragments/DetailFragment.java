package google.com.filmie.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import google.com.filmie.R;
import google.com.filmie.constants.ApiKey;
import google.com.filmie.constants.MovieConstants;
import google.com.filmie.models.Movie;
import google.com.filmie.models.Review;
import google.com.filmie.models.ReviewList;
import google.com.filmie.networks.MovieService;
import google.com.filmie.networks.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    @BindView(R.id.full_content_detail_fragment)
    LinearLayout fullContainer;

    @Nullable
    @BindView((R.id.collapsing_toolbar))
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.movie_poster_image)
    ImageView posterImageView;

    @BindView(R.id.detail_content_fragment_nested_scroll)
    NestedScrollView detailContainer;


    // Overview Card
    @BindView(R.id.detail_overview_card_title)
    TextView movieOverviewTitleTv;

    @BindView(R.id.detail_overview_card_ratings)
    TextView movieRatingsTv;

    @BindView(R.id.detail_overview_card_content)
    TextView movieOverviewContentTv;

    @BindView(R.id.detail_orginal_title_tv)
    TextView movieOriginalTitleTv;

    @BindView(R.id.detail_release_date_tv)
    TextView movieReleaseDateTv;

    @BindView(R.id.detail_orginal_language_tv)
    TextView movieOriginalLanguageTv;

    // Reviews Card

    @BindView(R.id.no_reviews_tv)
    TextView noReviewsTv;

    @BindView(R.id.reviews_container)
    LinearLayout reviewsContainer;

    @BindView(R.id.review_progressbar)
    ProgressBar mProgressBar;

    View mView;
    private Movie mMovie;
    private Unbinder unbinder;
    private List<Review> mReviews = new ArrayList<Review>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            if (getArguments().containsKey("movie")) {
                mMovie = (Movie) getArguments().getSerializable("movie");
            }
        } else {
            fullContainer.setVisibility(View.GONE);
            mCollapsingToolbar.setTitle("Select a movie. #Filmie");
        }

        //addBackHomeArrow(mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupDetails();
        getReviews();
    }

    private void setupDetails() {
        String imageUrl = MovieConstants.IMAGE_BASE_URL + MovieConstants.IMAGE_SIZE_LARGE +
                mMovie.getPosterPath();

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.place_holder)
                .into(posterImageView);

        mCollapsingToolbar.setTitle(mMovie.getTitle());

        movieOverviewContentTv.setText(mMovie.getOverview());
        movieRatingsTv.setText(Double.toString(mMovie.getAverageVote()) + "/10");

        movieOriginalTitleTv.setText(mMovie.getOriginalTitle());
        movieReleaseDateTv.setText(mMovie.getReleaseDate());
        movieOriginalLanguageTv.setText(mMovie.getOriginalLanguage());
    }

    private void getReviews() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        MovieService service = RetrofitBuilder.buildRetrofit().create(MovieService.class);

        Call<ReviewList> call = service.getReviewsList(mMovie.getId(), ApiKey.KEY);
        call.enqueue(new retrofit2.Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

                if (response.code() == 200) {
                    ReviewList reviewList = response.body();
                    for (int i = 0; i < reviewList.getResults().size(); i++) {
                        mReviews.add(reviewList.results.get(i));
                    }
                    //Toast.makeText(getContext(), mReviews.get(0).getAuthor(), Toast.LENGTH_SHORT).show();
                }
                addReviews();
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                noReviewsTv.setText("Error fetching reviews!!");
                noReviewsTv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addReviews() {
        if (mReviews.isEmpty()) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            noReviewsTv.setText("No review found!!");
            noReviewsTv.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            final LayoutInflater inflater = LayoutInflater.from(getActivity());
            for (int i = 0; i < mReviews.size(); i++) {
                Review review = mReviews.get(i);

                final View reviewView = inflater.inflate(R.layout.layout_review_single, reviewsContainer, false);
                TextView reviewAuthor = ButterKnife.findById(reviewView, R.id.review_by);
                TextView reviewContent = ButterKnife.findById(reviewView, R.id.review_content);
                reviewAuthor.setText("Author: " + review.getAuthor());
                reviewContent.setText(review.getContent());
                reviewsContainer.addView(reviewView);
                if (i < mReviews.size() - 1) {
                    final View dividerView = inflater.inflate(R.layout.divider_content,
                            reviewsContainer, false);
                    View divider = ButterKnife.findById(dividerView, R.id.divider_content);
                    reviewsContainer.addView(divider);
                }
            }
            noReviewsTv.setVisibility(View.GONE);
        }
    }

    private void addBackHomeArrow(View rootView) {
        final Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
