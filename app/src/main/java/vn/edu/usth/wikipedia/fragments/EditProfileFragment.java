package vn.edu.usth.wikipedia.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;

/**
 * Fragment for editing user profile information.
 */
public class EditProfileFragment extends Fragment {

    private EditText editName;
    private EditText editAge;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private EditText yearInput;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editUsername;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false); // Inflate the fragment layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        editName = view.findViewById(R.id.edit_name);
        editAge = view.findViewById(R.id.edit_age);
        daySpinner = view.findViewById(R.id.day_spinner);
        monthSpinner = view.findViewById(R.id.month_spinner);
        yearInput = view.findViewById(R.id.year_input);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
        editUsername = view.findViewById(R.id.edit_username);
        saveButton = view.findViewById(R.id.save_button);

        // Set up spinners with dummy data
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.day_array, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.month_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // Load current user data from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        editName.setText(prefs.getString("userName", ""));
        editAge.setText(String.valueOf(prefs.getInt("userAge", 0)));
        daySpinner.setSelection(prefs.getInt("userDay", 0));  // Set spinner selection based on saved index
        monthSpinner.setSelection(prefs.getInt("userMonth", 0));  // Set spinner selection based on saved index
        yearInput.setText(prefs.getString("userYear", ""));
        editEmail.setText(prefs.getString("userEmail", ""));
        editPassword.setText(prefs.getString("userPassword", ""));
        editUsername.setText(prefs.getString("userUsername", ""));

        // Set up save button click listener
        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        // Save updated user data to SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userName", editName.getText().toString());
        editor.putInt("userAge", Integer.parseInt(editAge.getText().toString()));
        editor.putInt("userDay", daySpinner.getSelectedItemPosition());
        editor.putInt("userMonth", monthSpinner.getSelectedItemPosition());
        editor.putString("userYear", yearInput.getText().toString());
        editor.putString("userEmail", editEmail.getText().toString());
        editor.putString("userPassword", editPassword.getText().toString());
        editor.putString("userUsername", editUsername.getText().toString());
        editor.apply();

        // Navigate back to UserFragment
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
