package com.example.happydoniatestproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.happydoniatestproject.R;


public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    private final String city;
    public HeaderAdapter(String city) {
        this.city = city;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_item, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderAdapter.HeaderViewHolder holder, int position) {
        holder.location_header.setText(this.city);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView location_header;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            location_header = (TextView) itemView.findViewById(R.id.location_header);
        }
    }
}
