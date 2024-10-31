package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.API.ApiCallTask;
import com.example.tripbuddy.API.ApiCallback;
import com.example.tripbuddy.Adapters.DestinationAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import com.example.tripbuddy.Models.Destination;

public class HomeActivity extends Fragment implements ApiCallback {

    // Initialize destination data
    private final List<Destination> destinationList = new ArrayList<>();
    private RecyclerView destinationRecyclerView;
    public HomeActivity() {
        super(R.layout.activity_home);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false); // Inflate the layout for this fragment

        // Set username
        TextView username = view.findViewById(R.id.textHeader);
        TextView viewAllText = view.findViewById(R.id.viewAllText);
        String name = UserSession.getInstance().getEmail();
        username.setText(name);
        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        ImageView notificationImageView = view.findViewById(R.id.notificationIcon);

        // Setup RecyclerView
        destinationRecyclerView = view.findViewById(R.id.destinationRecyclerView);
        destinationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Load destination data
        loadDestinationData();

        // Setup search all
        @SuppressLint("CutPasteId") TextView searchAll = view.findViewById(R.id.viewAllText);

        searchAll.setOnClickListener(v -> {
            // Chuyển đổi sang SecondFragment
            SearchActivity secondFragment = new SearchActivity();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment) // fragment_container là ID của container trong Activity
                    .addToBackStack(null) // Tùy chọn thêm vào backstack để quay lại fragment trước đó
                    .commit();

            // Cập nhật mục đã chọn trên BottomNavigationView
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.search);
        });


        profileImageView.setOnClickListener(v -> {
            // Chuyển đổi sang SecondFragment
            ProfileActivity secondFragment = new ProfileActivity();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();

            // Cập nhật mục đã chọn trên BottomNavigationView
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.profile);
        });

        notificationImageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        });

        return view;
    }

    // This method will load dummy data, replace it with real data fetching logic
    private void loadDestinationData() {
        // API URL for getting the list of users
        String apiUrl = "http://localhost:5000/api/places";  // Replace with your local or server URL
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
                double latitude  = desObject.getDouble("Description");

                Destination destination = new Destination(id, name,description,R.drawable.sample_destination, longitude, latitude  );
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
}
