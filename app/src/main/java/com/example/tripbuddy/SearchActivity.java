package com.example.tripbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.API.ApiCallTask;
import com.example.tripbuddy.API.ApiCallback;
import com.example.tripbuddy.Adapters.DestinationAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.example.tripbuddy.Models.Destination;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActivity extends Fragment implements ApiCallback {

    // Initialize destination data
    private List<Destination> destinationList = new ArrayList<>();
    private RecyclerView destinationRecyclerView;

    public SearchActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_search, container, false);

        // Initialize RecyclerView
        destinationRecyclerView = view.findViewById(R.id.destinationRecyclerView);

        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);  // Use getContext() or requireContext()
        destinationRecyclerView.setLayoutManager(gridLayoutManager);

        // Load Destination Data
        loadDestinationData();

        // Set Adapter
        DestinationAdapter destinationAdapter = new DestinationAdapter(destinationList, getContext());  // Pass context to the adapter
        destinationRecyclerView.setAdapter(destinationAdapter);

        // Set back button and cancel button

        // Set search all text
        AtomicReference<ImageView> backButton = new AtomicReference<>(view.findViewById(R.id.backButton));
        backButton.get().setOnClickListener(this::onClick);

        TextView cancelButton = view.findViewById(R.id.textCancel);
        cancelButton.setOnClickListener(this::onClick);

        TextView searchBar = view.findViewById(R.id.searchBar);
        ImageView searchButton = view.findViewById(R.id.searchIcon);

        searchBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Hide RecyclerView when search bar is focused
                destinationRecyclerView.setVisibility(View.GONE);
            } else {
                // Show RecyclerView when search bar loses focus
                destinationRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        searchButton.setOnClickListener(view1 -> {
            /* Toggle visibility of RecyclerView based on focus */
            destinationRecyclerView.setVisibility(view1.hasFocus() ? View.GONE : View.VISIBLE);
            searchBar.clearFocus();
        });
        return view;
    }

    // This method will load dummy data, replace it with real data fetching logic
    private void loadDestinationData() {
        // API URL for getting the list of users
        String apiUrl = "https://localhost:5000/api/places";  // Replace with your local or server URL
        new ApiCallTask(this).execute(apiUrl);
    }

    @Override
    public void onSuccess(String result) {
        updateUserList(result);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), "Error fetching Destinations: " + error, Toast.LENGTH_SHORT).show();
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
            DestinationAdapter destinationAdapter = new DestinationAdapter(destinationList, getContext());
            destinationRecyclerView.setAdapter(destinationAdapter);
            destinationAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to parse user data", Toast.LENGTH_SHORT).show();
        }
    }
    private void onClick(View v) {
        // Chuyển đổi sang SecondFragment
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment) // fragment_container là ID của container trong Activity
                .addToBackStack(null) // Tùy chọn thêm vào backstack để quay lại fragment trước đó
                .commit();

        // Cập nhật mục đã chọn trên BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
