package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;

/**
 * Fragment for handling donations.
 */
public class DonateFragment extends Fragment {

    private RadioGroup donationAmountGroup; // Group for donation amount options
    private EditText customDonationInput; // Input field for custom donation amount

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donate, container, false); // Inflate the layout for this fragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donationAmountGroup = view.findViewById(R.id.donation_amount_group); // Initialize RadioGroup
        customDonationInput = view.findViewById(R.id.custom_donation_input); // Initialize EditText
        Button donateButton = view.findViewById(R.id.donate_button); // Initialize donate button
        Button backButton = view.findViewById(R.id.back_to_main_button); // Initialize back button

        // Show custom donation input field when "Custom Amount" is selected
        donationAmountGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.donate_custom) {
                customDonationInput.setVisibility(View.VISIBLE); // Show custom donation input
            } else {
                customDonationInput.setVisibility(View.GONE); // Hide custom donation input
            }
        });

        donateButton.setOnClickListener(v -> {
            int selectedId = donationAmountGroup.getCheckedRadioButtonId(); // Get selected RadioButton ID
            String donationAmount;

            if (selectedId == R.id.donate_custom) {
                donationAmount = customDonationInput.getText().toString().trim(); // Get custom donation amount
                if (donationAmount.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a custom amount", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                RadioButton selectedButton = view.findViewById(selectedId); // Get selected RadioButton
                donationAmount = selectedButton.getText().toString(); // Get amount from RadioButton
            }

            // Implement donation functionality here
            Toast.makeText(getContext(), "Donating " + donationAmount, Toast.LENGTH_SHORT).show(); // Show donation amount
        });

        // Handle back to main page
        backButton.setOnClickListener(v -> {
            // Replace current fragment with SearchFragment and show main page
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();
        });
    }
}
