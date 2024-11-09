package vn.edu.usth.wikipedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
        setContentView(R.layout.activity_payment);

        String donationAmount = getIntent().getStringExtra("donationAmount");

        TextView donationTextView = findViewById(R.id.donation_amount_text);
        donationTextView.setText("You are donating: " + donationAmount);

        ImageButton closeButton = findViewById(R.id.close_payment_button);
        closeButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

    public void onPaymentMethodClick(View view) {
        int id = view.getId(); 

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
