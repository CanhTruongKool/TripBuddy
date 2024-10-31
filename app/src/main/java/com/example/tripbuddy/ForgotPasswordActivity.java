package com.example.tripbuddy;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    Button resetPasswordButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Bind views
        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backButton = findViewById(R.id.backButton);


        // Reset Password button click
        resetPasswordButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate API call to reset the password (for example purposes)
                showResetPasswordDialog();
            }
        });

        backButton.setOnClickListener(view ->{
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void showResetPasswordDialog() {
        // Inflate custom layout for dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.dialog_password_reset, null));

        // Create and show dialog
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        // Close the dialog and move to OTP Verification screen after a few seconds
        alertDialog.setOnDismissListener(dialog -> {
            // Navigate to OTP Verification screen
            Intent intent = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
            intent.putExtra("email", emailEditText.getText().toString());
            startActivity(intent);
        });
    }
}

