package com.example.tripbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.ReviewAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.Models.Review;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> reviews; // Assuming you have a Review model

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews); // Your activity layout

        recyclerView = findViewById(R.id.recyclerViewReviews);
        ImageView writeReviewButton = findViewById(R.id.btnWriteReview); // ID of your button to write review

        String destinationName = getIntent().getStringExtra("destinationName");

        // Use the destination name in your layout, for example to set the title
        TextView tvDestinationName = findViewById(R.id.tvReviewsTitle);
        tvDestinationName.setText(String.format("Review of %s", destinationName));

        // Initialize the reviews list and seed it with example reviews
        reviews = new ArrayList<>();
        seedReviews(); // Call the method to seed reviews

        reviewAdapter = new ReviewAdapter(reviews); // Replace with your adapter class
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewAdapter);

        // Set up the Write Review button
        writeReviewButton.setOnClickListener(v -> showReviewDialog());

        // Handle back button press to return to the previous screen
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void seedReviews() {
        // Add example reviews to the list
        reviews.add(new Review("Sajib Rahman", "Chồ này là cmt nè .....", "Just Now"));
        reviews.add(new Review("Adom Shafi", "You Cool! 😄 Let's meet at 18:00 near the traveling!", "Yesterday"));
        reviews.add(new Review("HR Rumen", "You: Hey, will you come to the party on Saturday?", "2 days ago"));
        reviews.add(new Review("Anjelina", "Thank you for coming! Your review was helpful.", "3 days ago"));
        reviews.add(new Review("Alexa Shorna", "Looking forward to our next meeting!", "4 days ago"));
    }

    private void showReviewDialog() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Write Your Review");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_write_review, null);
        builder.setView(dialogView);

        // Get the EditText from the dialog
        EditText editTextReview = dialogView.findViewById(R.id.etReviewInput);
        String email = UserSession.getInstance().getEmail();
        // Set positive button to save the review
        builder.setPositiveButton("Submit", (dialog, which) -> {
            String reviewText = editTextReview.getText().toString().trim();
            if (!reviewText.isEmpty()) {
                // Create a new Review object
                Review newReview = new Review(email, reviewText, currentTime); // You can change "Your Name" to the actual user's name
                // Add the review to the list and notify the adapter
                reviews.add(newReview);
                reviewAdapter.notifyItemInserted(reviews.size() - 1);
                recyclerView.scrollToPosition(reviews.size() - 1); // Scroll to the new review
                Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set negative button to cancel
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        // If you want to pass any data back to the previous activity, you can set the result here
        // For example:
        // Intent returnIntent = new Intent();
        // setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}

