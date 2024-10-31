package com.example.tripbuddy.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.DestinationDetailActivity;
import com.example.tripbuddy.R;

import java.util.List;
import com.example.tripbuddy.Models.Destination;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    public List<Destination> destinationList;
    public Context context;

    public DestinationAdapter(List<Destination> destinationList, Context context) {
        this.destinationList = destinationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.destinationName.setText(destination.getName());
        holder.destinationImage.setImageResource(destination.getImageResId());  // Assuming you are using drawable resources.

        // Handle click event on each destination item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DestinationDetailActivity.class);
            intent.putExtra("destinationTitle", destination.getName());
            intent.putExtra("destinationImage", R.drawable.example_detail);
            intent.putExtra("destinationDescription", destination.getLocation());
            intent.putExtra("destinationShortDescription", "12345");
            intent.putExtra("destinationFullDescription", "123456789");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView destinationName, destinationLocation;
        ImageView destinationImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationName = itemView.findViewById(R.id.destinationName);
            destinationLocation = itemView.findViewById(R.id.destinationLocation);
            destinationImage = itemView.findViewById(R.id.destinationImage);
        }
    }
}
