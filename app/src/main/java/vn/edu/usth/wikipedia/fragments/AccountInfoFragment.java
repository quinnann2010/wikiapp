package vn.edu.usth.wikipedia.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.SettingActivity;
import vn.edu.usth.wikipedia.database.DatabaseHelper;

public class AccountInfoFragment extends Fragment {

    private TextView usernameText, emailText, phoneText, genderText, dobText;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameText = view.findViewById(R.id.username_text);
        emailText = view.findViewById(R.id.email_text);
        phoneText = view.findViewById(R.id.phone_text);
        genderText = view.findViewById(R.id.gender_text);
        dobText = view.findViewById(R.id.dob_text);
        ImageButton closeUser = view.findViewById(R.id.close_user_button);
        Button editButton = view.findViewById(R.id.edit_button);

        closeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(v -> navigateToEditProfile());


        dbHelper = new DatabaseHelper(getContext());

        // Retrieve logged-in username from shared preferences
        String username = requireActivity().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE).getString("username", "Guest");

        // Fetch user information from database
        loadUserInfo(username);
    }

    private void loadUserInfo(String username) {
        Cursor cursor = dbHelper.getUserData(username);
        if (cursor != null && cursor.moveToFirst()) {
            usernameText.setText(cursor.getString(cursor.getColumnIndex("username")));
            emailText.setText(cursor.getString(cursor.getColumnIndex("email")));
            phoneText.setText(cursor.getString(cursor.getColumnIndex("phone")));
            genderText.setText(cursor.getString(cursor.getColumnIndex("gender")));
            dobText.setText(cursor.getString(cursor.getColumnIndex("dob")));
            cursor.close();
        } else {
            Toast.makeText(getContext(), "Unable to load user information", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToEditProfile() {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditAccountFragment()).addToBackStack(null).commit();
    }
}