package com.subdico.moviecatalogue4.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.subdico.moviecatalogue4.DetailActivity;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.model.ListData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.mainViewHolder> {

    private ArrayList<ListData> mData = new ArrayList<>();
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setmData(ArrayList<ListData> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public mainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new mainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final mainViewHolder holder, int position) {
        holder.bind(mData.get(position));
        holder.itemView.setOnClickListener(new CustomClickAdapter(position, new CustomClickAdapter.onCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.Extra, mData.get(position));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class mainViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName;

        mainViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.poster_view);
            tvName = itemView.findViewById(R.id.name_view);
        }

        void bind(final ListData listData){
            Glide.with(itemView.getContext())
                    .load(listData.getPoster_path())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_image)
                            .override(350 , 550)
                            .error(R.drawable.ic_warning))
                    .into(imgPoster);
            tvName.setText(listData.getName());
            Log.d("adapter", "bind: " + listData.getPoster_path());
        }
    }
}
