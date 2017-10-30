package com.example.joseph.moviedbchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joseph.moviedbchallenge.model.Result;

import java.util.ArrayList;
import java.util.List;


public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    List<Result> items = new ArrayList<>();
    Context context;

    private OnLoadMoreListener onLoadMoreListener;
//    private boolean isLoading;
    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private Activity activity;

    public ItemListAdapter(RecyclerView recyclerView, Activity activity, List<Result> items) {
        this.items = items;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
                if(dy > 0) //check for scroll down
                {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

//                    if (!isLoading)
//                    {
                        if (lastVisibleItem >= (totalItemCount-visibleThreshold))
                        {
//                            isLoading = true;
//                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            if(onLoadMoreListener!=null){
                                onLoadMoreListener.onLoadMore();
                            }
                        }
//                    }
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

//        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(view);
//        } else if (viewType == VIEW_TYPE_LOADING) {
//            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
//        return null;

//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Item item = items.get(position);
//        holder.item = item;
//
//        holder.tvName.setText(item.getName());
//        holder.tvPrice.setText(item.getSalePrice().toString());
//        Glide.with(context).load(item.getThumbnailImage()).into(holder.ivImage);

//        if (holder instanceof ItemViewHolder) {
//            Contact contact = contacts.get(position);
//            UserViewHolder userViewHolder = (UserViewHolder) holder;
//            userViewHolder.phone.setText(contact.getEmail());
//            userViewHolder.email.setText(contact.getPhone());
            Result item = items.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.item = item;

            itemViewHolder.tvName.setText(item.getTitle());
            itemViewHolder.tvReleaseDate.setText(item.getReleaseDate().toString());
            String imagepath = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();
            Glide.with(context).load(imagepath).into(itemViewHolder.ivImage);
//        } else if (holder instanceof LoadingViewHolder) {
//            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
//            loadingViewHolder.progressBar.setIndeterminate(true);
//        }

    }

    @Override
    public int getItemCount() {
//        return items.size();
        return items == null ? 0 : items.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//    }

//    public void setLoaded() {
//        isLoading = false;
//    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvReleaseDate;
        Result item;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra("movie", item);
                    context.startActivity(intent);
                }
            });
        }
    }

}
