package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.text.TextUtils;
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
import vn.edu.usth.wikipedia.PaymentActivity; // Import the payment screen

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
<<<<<<< HEAD

        ImageButton closeDonate = view.findViewById(R.id.close_donate_button);

        closeDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close and return to main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
=======
        Button backButton = view.findViewById(R.id.back_to_main_button); // Initialize back button
>>>>>>> 379b9e3ce88c3743090609deacb69a5b9595535c

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
            String donationAmount = "";

            if (selectedId == R.id.donate_custom) {
                donationAmount = customDonationInput.getText().toString().trim(); // Get custom donation amount

                // Input validation for custom amount
                if (TextUtils.isEmpty(donationAmount)) {
                    Toast.makeText(getContext(), "Please enter a custom amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidDonationAmount(donationAmount)) {
                    Toast.makeText(getContext(), "Please enter a valid amount greater than zero", Toast.LENGTH_SHORT).show();
                    return;
                }

            } else {
                // Get amount from the selected RadioButton
                RadioButton selectedButton = view.findViewById(selectedId);
                donationAmount = selectedButton.getText().toString();
            }

            // Proceed to payment method screen with the donation amount
            proceedToPayment(donationAmount);
        });
    }

<<<<<<< HEAD
    // Helper method to check if the donation amount is valid
    private boolean isValidDonationAmount(String amount) {
        try {
            double donation = Double.parseDouble(amount);
            return donation > 0; // Amount must be greater than zero
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }
    }

    // Method to proceed to the payment method screen
    private void proceedToPayment(String donationAmount) {
        Intent intent = new Intent(getActivity(), PaymentActivity.class); // Navigate to payment screen
        intent.putExtra("donationAmount", donationAmount); // Pass the donation amount
        startActivity(intent);
=======
        // Handle back to main page
        backButton.setOnClickListener(v -> {
            // Replace current fragment with SearchFragment and show main page
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();
        });
>>>>>>> 379b9e3ce88c3743090609deacb69a5b9595535c
    }
}
