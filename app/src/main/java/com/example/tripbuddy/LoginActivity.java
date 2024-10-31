package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddy.Adapters.UserSession;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signInButton;
    TextView signUpButton, forgotPasswordText;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpPrompt);
        forgotPasswordText  = findViewById(R.id.forgotPasswordText);

        signInButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Call API to authenticate user (Retrofit or HttpUrlConnection)
                loginUser(email, password);
            }
        });

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPasswordText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        // Implement API call to your C# backend here
        // For example, using Retrofit or another HTTP library
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // Set email in UserSession
        UserSession.getInstance().setEmail(email);
        intent.putExtra("name", email);
        startActivity(intent);
    }
}
