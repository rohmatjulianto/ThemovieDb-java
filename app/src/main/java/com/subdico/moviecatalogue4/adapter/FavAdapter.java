package com.subdico.moviecatalogue4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.subdico.moviecatalogue4.DetailActivity;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private ArrayList<ListData> mDataFav = new ArrayList<>();
    private Context context;

    public FavAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ListData> getmDataFav() {
        return mDataFav;
    }

    public void setmDataFav(ArrayList<ListData> items) {
        mDataFav.clear();
        mDataFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fav, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavAdapter.FavViewHolder holder, int position) {
        holder.bind(mDataFav.get(position));
        holder.itemView.setOnClickListener(new CustomClickAdapter(position, new CustomClickAdapter.onCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.Extra, mDataFav.get(position));
                context.startActivity(intent);

            }
        }));

    }

    @Override
    public int getItemCount() {
        return mDataFav.size();
    }


    class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFav;
        TextView tvName, tvRelease;
        RatingBar ratingBar;
        ImageButton imgButton;

        FavViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_fav);
            tvRelease = itemView.findViewById(R.id.tv_release_fav);
            imgFav = itemView.findViewById(R.id.img_fav);
            ratingBar = itemView.findViewById(R.id.rating_bar_fav);
            imgButton = itemView.findViewById(R.id.img_btn_fav);
        }

        void bind(final ListData listData) {
            Glide.with(itemView.getContext())
                    .load(listData.getPoster_path())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_image)
                            .override(250 , 450)
                            .error(R.drawable.ic_warning))
                    .into(imgFav);
            tvName.setText(listData.getName());
            tvRelease.setText(listData.getFirst_air_date());
            float d = listData.getVote_average().floatValue()/2;
            ratingBar.setRating(d);
        }
    }

}
