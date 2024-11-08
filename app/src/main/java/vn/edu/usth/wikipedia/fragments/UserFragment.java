package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;

public class UserFragment extends Fragment {

    private TextView userNameTextView;
    private TextView userAgeTextView;
    private TextView userDobTextView;
    private TextView userEmailTextView;
    private TextView userPasswordTextView;  // Consider masking or removing this
    private TextView userUsernameTextView;
    private Button editProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views
        userNameTextView = view.findViewById(R.id.user_name);
        userAgeTextView = view.findViewById(R.id.user_age);
        userDobTextView = view.findViewById(R.id.user_dob);
        userEmailTextView = view.findViewById(R.id.user_email);
        userPasswordTextView = view.findViewById(R.id.user_password);
        userUsernameTextView = view.findViewById(R.id.user_username);
        editProfileButton = view.findViewById(R.id.edit_profile_button);

        // Load user data from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        String userName = prefs.getString("userName", "N/A");
        int userAge = prefs.getInt("userAge", 0);
        String userDob = prefs.getString("userDob", "N/A");
        String userEmail = prefs.getString("userEmail", "N/A");
        String userPassword = prefs.getString("userPassword", "N/A");
        String userUsername = prefs.getString("userUsername", "N/A");

        // Set user data to views
        userNameTextView.setText("Name: " + userName);
        userAgeTextView.setText("Age: " + userAge);
        userDobTextView.setText("Date of Birth: " + userDob);
        userEmailTextView.setText("Email: " + userEmail);
        userPasswordTextView.setText("Password: ***");  // Masked password
        userUsernameTextView.setText("Username: " + userUsername);

        // Set up edit profile button click listener
        editProfileButton.setOnClickListener(v -> navigateToEditProfile());
    }

    private void navigateToEditProfile() {
        // Replace this with the actual edit profile fragment or activity
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EditProfileFragment())
                .addToBackStack(null)
                .commit();
    }
}
