package vn.edu.usth.wikipedia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.PaymentActivity; // Import the payment screen

/**
 * Fragment for handling donations.
 */
public class DonateFragment extends Fragment {

    private RadioGroup donationAmountGroup;
    private EditText customDonationInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donationAmountGroup = view.findViewById(R.id.donation_amount_group);
        customDonationInput = view.findViewById(R.id.custom_donation_input);
        Button donateButton = view.findViewById(R.id.donate_button);


        ImageButton closeDonate = view.findViewById(R.id.close_donate_button);

        closeDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        donationAmountGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.donate_custom) {
                customDonationInput.setVisibility(View.VISIBLE);
            } else {
                customDonationInput.setVisibility(View.GONE);
            }
        });

        donateButton.setOnClickListener(v -> {
            int selectedId = donationAmountGroup.getCheckedRadioButtonId();
            String donationAmount = "";

            if (selectedId == R.id.donate_custom) {
                donationAmount = customDonationInput.getText().toString().trim();

                if (TextUtils.isEmpty(donationAmount)) {
                    Toast.makeText(getContext(), "Please enter a custom amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidDonationAmount(donationAmount)) {
                    Toast.makeText(getContext(), "Please enter a valid amount greater than zero", Toast.LENGTH_SHORT).show();
                    return;
                }

            } else {
                RadioButton selectedButton = view.findViewById(selectedId);
                donationAmount = selectedButton.getText().toString();
            }

            proceedToPayment(donationAmount);
        });
    }


    private boolean isValidDonationAmount(String amount) {
        try {
            double donation = Double.parseDouble(amount);
            return donation > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void proceedToPayment(String donationAmount) {
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra("donationAmount", donationAmount);
        startActivity(intent);
    }
}
