package vn.edu.usth.wikipedia.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.SettingActivity;
import vn.edu.usth.wikipedia.database.DatabaseHelper;

public class EditAccountFragment extends Fragment {

    private EditText emailEdit, phoneEdit, dobEdit;
    private Spinner genderEdit;
    private DatabaseHelper dbHelper;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        emailEdit = view.findViewById(R.id.email_edit);
        phoneEdit = view.findViewById(R.id.phone_edit);
        genderEdit = view.findViewById(R.id.gender_edit_spinner);
        dobEdit = view.findViewById(R.id.dob_edit);
        Button saveButton = view.findViewById(R.id.save_button);
        ImageButton closeEdit = view.findViewById(R.id.close_edit_button);

        closeEdit.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        dbHelper = new DatabaseHelper(getContext());

        // Retrieve logged-in username from shared preferences
        username = requireActivity().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE).getString("username", "Guest");

        // Load current data
        loadUserData();

        // Set up save button
        saveButton.setOnClickListener(v -> saveUpdatedInfo());
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUserData(username);
        if (cursor != null && cursor.moveToFirst()) {
            emailEdit.setText(cursor.getString(cursor.getColumnIndex("email")));
            phoneEdit.setText(cursor.getString(cursor.getColumnIndex("phone")));
            dobEdit.setText(cursor.getString(cursor.getColumnIndex("dob")));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));

            // Set gender selection based on retrieved value
            if (gender.equalsIgnoreCase("Male")) {
                genderEdit.setSelection(0);
            } else if (gender.equalsIgnoreCase("Female")) {
                genderEdit.setSelection(1);
            } else {
                genderEdit.setSelection(2); // Assume "Other" or a default option
            }
            cursor.close();
        }
    }

    private void saveUpdatedInfo() {
        String email = emailEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String dob = dobEdit.getText().toString();
        String gender = genderEdit.getSelectedItem().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("phone", phone);
        values.put("gender", gender);
        values.put("dob", dob);

        int result = db.update("users", values, "username=?", new String[]{username});
        db.close();

        if (result > 0) {
            Toast.makeText(getContext(), "Information updated successfully", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "Failed to update information", Toast.LENGTH_SHORT).show();
        }
    }
}