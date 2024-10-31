package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText nameEditText, emailEditText, passwordEditText;
    Button signUpButton;
    TextView alreadyHaveAccountText;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Bind views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        alreadyHaveAccountText = findViewById(R.id.alreadyHaveAccountText);
        backButton = findViewById(R.id.backButton);

        // Sign-Up button click
        signUpButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Call API to sign up the user (Retrofit or HttpUrlConnection)
                registerUser(name, email, password);
            }
        });

        // Already have an account click
        alreadyHaveAccountText.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String name, String email, String password) {
        // API Call Logic (Retrofit or HttpUrlConnection)
        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
    }
}

