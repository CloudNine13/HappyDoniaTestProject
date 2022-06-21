package com.example.happydoniatestproject.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.happydoniatestproject.R;
import java.util.List;
import java.util.Map;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RVViewHolder> {

    private final List<Map<String, String>> list;

    public RecyclerViewAdapter(List<Map<String, String>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView distance;
        ImageView image;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.city_string);
            distance = itemView.findViewById(R.id.distance_string);
            image = itemView.findViewById(R.id.item_image_view);
        }

        public void bind(Map<String, String> item) {
            title.setText(item.get("title"));
            distance.setText(item.get("distance"));
            String URL = item.get("image");
            if(URL != null) {
                setImage(URL);
            }
            itemView.setOnClickListener(view -> {
                String url = "https://wikipedia.org/wiki/" + item.get("title");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                view.getContext().startActivity(i);
            });
        }

        public void setImage(String URL){
            Glide.with(itemView.getContext()).load(URL).into(image);
        }
    }
}
