package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.SettingActivity;
import vn.edu.usth.wikipedia.database.DatabaseHelper;

public class UserFragment extends Fragment {

    private TextView userNameTextView;
    private TextView userDobTextView;
    private TextView userEmailTextView;
    private TextView userUsernameTextView;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        userNameTextView = view.findViewById(R.id.user_name);
        userDobTextView = view.findViewById(R.id.user_dob);
        userEmailTextView = view.findViewById(R.id.user_email);
        userUsernameTextView = view.findViewById(R.id.user_username);
//        Button editProfileButton = view.findViewById(R.id.edit_profile_button);
        Button backToSettingButton = view.findViewById(R.id.back_to_setting_button);
        ImageButton closeUser = view.findViewById(R.id.close_user_button);

        closeUser.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        backToSettingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(requireContext());

        // Load logged-in user data
        loadUserData();

        // Set up edit profile button click listener
//        editProfileButton.setOnClickListener(v -> navigateToEditProfile());
    }

    private void loadUserData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int loggedInUserId = prefs.getInt("loggedInUserId", -1);  // Retrieve the logged-in user ID

        if (loggedInUserId != -1) {  // Ensure there is a valid user ID
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    "users",  // Table name
                    null,  // Select all columns
                    "id = ?",  // Where clause to find the user by ID
                    new String[]{String.valueOf(loggedInUserId)},  // Where clause arguments
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int userNameIndex = cursor.getColumnIndex("username");
                int userEmailIndex = cursor.getColumnIndex("email");
                int userDobIndex = cursor.getColumnIndex("dob");

                // Check if the column index is valid
                String userName = (userNameIndex != -1) ? cursor.getString(userNameIndex) : "N/A";
                String userEmail = (userEmailIndex != -1) ? cursor.getString(userEmailIndex) : "N/A";
                String userDob = (userDobIndex != -1) ? cursor.getString(userDobIndex) : "N/A";

                // Set user data to views
                userNameTextView.setText("Name: " + userName);
                userDobTextView.setText("Date of Birth: " + userDob);
                userEmailTextView.setText("Email: " + userEmail);
                userUsernameTextView.setText("Username: " + userName);  // Assuming 'username' matches userName

                cursor.close();
            } else {
                // Show default message if no user data found
                userNameTextView.setText("Name: N/A");
                userDobTextView.setText("Date of Birth: N/A");
                userEmailTextView.setText("Email: N/A");
                userUsernameTextView.setText("Username: N/A");
            }
            db.close();
        }
    }

    private void navigateToEditProfile() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EditProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dbHelper.close();
    }
}
