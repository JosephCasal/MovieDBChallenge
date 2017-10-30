package com.example.joseph.moviedbchallenge;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joseph.moviedbchallenge.model.Result;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Result result = (Result) getIntent().getSerializableExtra("movie");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(result.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView movieReleaseDate = findViewById(R.id.tvMovieReleaseDate);
        ImageView imageView = findViewById(R.id.ivMovieImage);

        movieReleaseDate.setText(result.getReleaseDate());

        String imagepath = "https://image.tmdb.org/t/p/w500" + result.getPosterPath();

        Glide.with(this).load(imagepath).into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
