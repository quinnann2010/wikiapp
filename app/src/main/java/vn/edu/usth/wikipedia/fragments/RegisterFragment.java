package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.database.DatabaseHelper;

public class RegisterFragment extends Fragment {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private EditText yearInput;
    private EditText phoneInput;
    private Spinner genderSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameInput = view.findViewById(R.id.username_input);
        passwordInput = view.findViewById(R.id.password_input);
        emailInput = view.findViewById(R.id.email_input);
        daySpinner = view.findViewById(R.id.day_spinner);
        monthSpinner = view.findViewById(R.id.month_spinner);
        yearInput = view.findViewById(R.id.year_input);
        phoneInput = view.findViewById(R.id.phone_input);
        genderSpinner = view.findViewById(R.id.gender_spinner);
        Button registerButton = view.findViewById(R.id.register_button);
        ImageButton backButton = view.findViewById(R.id.close_reg_button);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.day_array, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.month_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        registerButton.setOnClickListener(v -> performRegistration());

        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void performRegistration() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String email = emailInput.getText().toString();
        String day = daySpinner.getSelectedItem().toString();
        String month = monthSpinner.getSelectedItem().toString();
        String year = yearInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String dob = day + "-" + month + "-" + year;

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || year.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        boolean success = dbHelper.addUser(username, password, email, phone, gender, dob);

        if (success) {
            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

}