package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
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

import vn.edu.usth.wikipedia.R;

public class ForgotPasswordFragment extends Fragment {

    private EditText emailInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false); // Inflate the layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailInput = view.findViewById(R.id.email_input);
        Button resetPasswordButton = view.findViewById(R.id.reset_password_button);
        ImageButton backButton = view.findViewById(R.id.close_forgot_button);

        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        resetPasswordButton.setOnClickListener(v -> performPasswordReset());
    }

    private void performPasswordReset() {
        String email = emailInput.getText().toString().trim(); // Get and trim email input

        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate password reset process (In a real app, you would send a request to a server)
        Toast.makeText(getContext(), "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();

        // Navigate back to the previous fragment
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
