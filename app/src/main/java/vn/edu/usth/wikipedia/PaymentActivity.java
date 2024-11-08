package vn.edu.usth.wikipedia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity to simulate a payment method selection screen.
 */
public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment); // Load the layout for payment

        // Get the donation amount passed from the previous screen
        String donationAmount = getIntent().getStringExtra("donationAmount");

        // Show the donation amount on the screen
        TextView donationTextView = findViewById(R.id.donation_amount_text);
        donationTextView.setText("You are donating: " + donationAmount);
    }

    public void onPaymentMethodClick(View view) {
        int id = view.getId(); // Get the ID of the clicked view

        // Handle different payment methods
        if (id == R.id.binance_payment) {
            Toast.makeText(this, "Binance selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mastercard_payment) {
            Toast.makeText(this, "MasterCard selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.paypal_payment) {
            Toast.makeText(this, "PayPal selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.stripe_payment) {
            Toast.makeText(this, "Stripe selected", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.applepay_payment) {
            Toast.makeText(this, "Apple Pay selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unknown Payment Method", Toast.LENGTH_SHORT).show();
        }
    }
}
