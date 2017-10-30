package com.example.joseph.moviedbchallenge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.joseph.moviedbchallenge.model.Result;
import com.example.joseph.moviedbchallenge.model.SearchResult;
import com.example.joseph.moviedbchallenge.remote.MovieData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView rvItems;

    List<Result> itemList = new ArrayList<>();
    private ItemListAdapter itemListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private EditText etSearch;
    private Activity activity;

    private int currentPage;
    private int totalPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        rvItems = findViewById(R.id.rvItems);
        etSearch = findViewById(R.id.etSearch);

        layoutManager = new LinearLayoutManager(getApplicationContext());

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        actionBar.setTitle("MovieDB Search");

    }

    public void search(View view) {

        Log.d(TAG, "search: ");

        currentPage = 1;
        totalPages = 0;

        final String search = etSearch.getText().toString();

        View currentFocus = this.getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }

        MovieData.search(search, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull SearchResult searchResult) {
                        itemList = searchResult.getResults();
                        totalPages = searchResult.getTotalPages();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        rvItems.setLayoutManager(layoutManager);
                        itemListAdapter = new ItemListAdapter(rvItems, activity, itemList);

                        itemListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {

                                if(currentPage < totalPages){
//                                    itemList.add(null);
//                                    itemListAdapter.notifyItemInserted(itemList.size() - 1);
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
//                                            itemList.remove(itemList.size() - 1);
//                                            itemListAdapter.notifyItemRemoved(itemList.size());

                                            currentPage++;
                                            getMoreSearch(search, currentPage);

                                        }
                                    });
                                }
                            }
                        });
                        rvItems.setAdapter(itemListAdapter);
                    }
                });

    }

    public void getMoreSearch(String search, int page){
        Log.d(TAG, "getMoreSearch: " + search + " " + page);
        MovieData.search(search, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull SearchResult searchResult) {

                        itemList.addAll(searchResult.getResults());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        itemListAdapter.notifyDataSetChanged();
//                        itemListAdapter.setLoaded();

                    }
                });
    }

}
