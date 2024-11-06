package vn.edu.usth.wikipedia.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        usernameEditText = view.findViewById(R.id.username_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        Button loginButton = view.findViewById(R.id.login_button);
        ImageButton closeLogin = view.findViewById(R.id.close_login_button);
        Button createButton = view.findViewById(R.id.create_button);
        Button forgotButton = view.findViewById(R.id.forgot_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ForgotPasswordFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        closeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        // Set up the click listener for the login button
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        // Retrieve username and password from EditText fields
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Normally, you would validate the credentials with a server or database
        // For demonstration, assume login is successful if fields are not empty

        // Save user data to SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userName", "John Doe"); // Replace with actual username
        editor.putInt("userAge", 30); // Replace with actual age
        editor.putString("userDob", "1994-09-15"); // Replace with actual date of birth
        editor.putString("userEmail", "john.doe@example.com"); // Replace with actual email
        editor.putString("userPassword", "password123"); // Replace with actual password
        editor.putString("userUsername", username); // Save the entered username
        editor.apply();

        // Navigate to the UserFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UserFragment())
                .addToBackStack(null)
                .commit();
    }
}
