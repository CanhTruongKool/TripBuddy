package com.example.tripbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.ImageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DestinationDetailActivity extends AppCompatActivity {

    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        TextView aboutDescription = findViewById(R.id.aboutDescription);
        TextView destinationTitle = findViewById(R.id.destinationTitle);
        ImageView headerImage = findViewById(R.id.headerImage);
        ImageView backButton = findViewById(R.id.backButton);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Initialize the list of images (you can fetch this dynamically)
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.example_detail); // Replace with your image resource IDs
        imageList.add(R.drawable.example_detail);
        imageList.add(R.drawable.example_detail);
        ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);

        // Get data from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("destinationTitle");
        int imageResId = intent.getIntExtra("destinationImage", -1);
        String shortDescription = intent.getStringExtra("destinationShortDescription");
        String fullDescription = intent.getStringExtra("destinationFullDescription");

        // Set initial data
        destinationTitle.setText(title);
        if (imageResId != -1) {
            headerImage.setImageResource(imageResId);
        }

        // Set short description and configure "Read more..."
        if (shortDescription != null && fullDescription != null) {
            aboutDescription.setText(shortDescription); // Set the short description initially
            aboutDescription.setText(addClickablePartTextViewResizable(shortDescription, fullDescription, aboutDescription, "Read more..."));
            aboutDescription.setMovementMethod(LinkMovementMethod.getInstance()); // Make links clickable
        }

        // Back button
        backButton.setOnClickListener(view -> {
            Intent intentBack = new Intent(DestinationDetailActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        TextView locationTextView = findViewById(R.id.location);

        // Set the click listener for the location TextView
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps();
            }
        });

        // Review Action
        TextView reviewTextView = findViewById(R.id.ratingText);
        reviewTextView.setOnClickListener(v ->{
            Intent intentReview = new Intent(DestinationDetailActivity.this, ReviewsActivity.class);
            intentReview.putExtra("destinationName",title );
            startActivity(intentReview);
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final String shortDescription, final String fullDescription,
                                                                     final TextView textView, final String expandableText) {
        // Create a SpannableStringBuilder with short description plus the expandable text
        SpannableStringBuilder ssb = new SpannableStringBuilder(shortDescription + " " + expandableText);

        // Add a ClickableSpan to the expandable text
        if (ssb.toString().contains(expandableText)) {
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    // Toggle between expanded and collapsed states
                    if (!isExpanded) {
                        // Expand text and update text view
                        textView.setText(String.format("%s Read less", fullDescription));
                        textView.setMaxLines(Integer.MAX_VALUE);  // Show full description
                        textView.setEllipsize(null);  // Remove ellipsis
                        isExpanded = true;
                    } else {
                        // Collapse text and update text view
                        textView.setText(String.format("%s %s", shortDescription, expandableText)); // Set short description with "Read more"
                        textView.setMaxLines(3);  // Limit text to 3 lines
                        textView.setEllipsize(TextUtils.TruncateAt.END);  // Show ellipsis
                        isExpanded = false;
                    }

                    // Reapply the clickable span after text is set
                    textView.setText(addClickablePartTextViewResizable(
                            fullDescription,
                            shortDescription,
                            textView,
                            isExpanded ? "Read less" : "Read more..."));
                    textView.setMovementMethod(LinkMovementMethod.getInstance());  // Make links clickable
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor("#397D54"));  // Change link color
                    ds.setUnderlineText(false);  // Remove underline
                }
            }, shortDescription.length() + 1, ssb.length(), 0); // Set span for clickable text
        }
        return ssb;
    }

    private void openGoogleMaps() {
        // Example location coordinates (latitude and longitude)
        double latitude = 13.803869539638479;  // Replace with your location's latitude
        double longitude = 109.21914813886895; // Replace with your location's longitude

        // Create a URI for the location
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
        startActivity(intent);
    }

    public void showFullScreenImage(int imageResId) {
        // Create a dialog for the full-screen image
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove title
        dialog.setContentView(R.layout.dialog_full_screen_image); // Layout for the dialog
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); // Set transparent background

        // Find the ImageView in the dialog layout and set the image
        ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
        fullScreenImageView.setImageResource(imageResId);

        // Set the dialog to dismiss when the image is clicked
        fullScreenImageView.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }
}
