package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.API.ApiCallTask;
import com.example.tripbuddy.API.ApiCallback;
import com.example.tripbuddy.Adapters.DestinationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.example.tripbuddy.Models.Destination;

import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryActivity extends AppCompatActivity implements ApiCallback {

    // Initialize destination data
    private List<Destination> destinationList = new ArrayList<>();
    private RecyclerView destinationRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize RecyclerView
        destinationRecyclerView = findViewById(R.id.destinationRecyclerView);

        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        destinationRecyclerView.setLayoutManager(gridLayoutManager);

        // Load Destination Data
        loadDestinationData();

        // Set Adapter
        DestinationAdapter destinationAdapter = new DestinationAdapter(destinationList, this); // Pass activity context to the adapter
        destinationRecyclerView.setAdapter(destinationAdapter);

        // Set back button and cancel button
        AtomicReference<ImageView> backButton = new AtomicReference<>(findViewById(R.id.backButton));
        backButton.get().setOnClickListener(this::onClick);

        TextView cancelButton = findViewById(R.id.textCancel);
        cancelButton.setOnClickListener(this::onClick);

    }

    private void onClick(View view) {
        Intent intent  = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // This method will load dummy data, replace it with real data fetching logic
    private void loadDestinationData() {
        // API URL for getting the list of users
        String apiUrl = "http://localhost:5000/api/Place";  // Replace with your local or server URL
        new ApiCallTask(this).execute(apiUrl);
    }

    @Override
    public void onSuccess(String result) {
        updateUserList(result);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, "Error fetching Destinations: " + error, Toast.LENGTH_SHORT).show();
    }

    private void updateUserList(String jsonResult) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            destinationList.clear(); // Clear the previous data

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject desObject = jsonArray.getJSONObject(i);
                String id = desObject.getString("PlcKey");
                String name = desObject.getString("PlcName");
                String description = desObject.getString("Description");
                double longitude = desObject.getDouble("Description");
                double latitude = desObject.getDouble("Description");

                Destination destination = new Destination(id, name, description, R.drawable.sample_destination, longitude, latitude);
                destinationList.add(destination); // Add to the list
            }
            // Setup adapter
            DestinationAdapter destinationAdapter = new DestinationAdapter(destinationList, this);
            destinationRecyclerView.setAdapter(destinationAdapter);
            destinationAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse user data", Toast.LENGTH_SHORT).show();
        }
    }

}
