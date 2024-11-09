package vn.edu.usth.wikipedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.wikipedia.fragments.AccountInfoFragment;
import vn.edu.usth.wikipedia.fragments.DonateFragment;
import vn.edu.usth.wikipedia.fragments.LanguageFragment;
import vn.edu.usth.wikipedia.fragments.LoginFragment;

public class SettingActivity extends AppCompatActivity {

    Button loginButtonText, donateButtonText, languageButtonText;
    ImageButton loginButton, donateButton, languageButton, closeButton, userImage;
    RelativeLayout settingBar;
    ImageView usthImage;
    TextView usernameText;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        loginButton = findViewById(R.id.login_button);
        donateButton = findViewById(R.id.donate_button);
        languageButton = findViewById(R.id.language_button);
        loginButtonText = findViewById(R.id.login_button_text);
        donateButtonText = findViewById(R.id.donate_button_text);
        languageButtonText = findViewById(R.id.language_button_text);
        closeButton = findViewById(R.id.close_more_button);
        settingBar = findViewById(R.id.setting_bar);
        usthImage = findViewById(R.id.usth_logo);
        userImage = findViewById(R.id.user_image);
        usernameText = findViewById(R.id.username_text);

        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String username = prefs.getString("username", "Guest");

        if (isLoggedIn) {
            loginButtonText.setText("Logout");
            usernameText.setText(username); // Display logged-in username
            loginButtonText.setOnClickListener(view -> logoutUser());
        } else {
            loginButtonText.setText("Login");
            loginButtonText.setOnClickListener(view -> openLoginFragment());
        }

        userImage.setOnClickListener(view -> openAccountInfoFragment());
        donateButton.setOnClickListener(view -> openDonateFragment());
        donateButtonText.setOnClickListener(view -> openDonateFragment());
        languageButton.setOnClickListener(view -> openLanguageFragment());
        languageButtonText.setOnClickListener(view -> openLanguageFragment());
        closeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void openAccountInfoFragment() {
        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        userImage.setVisibility(View.GONE);
        usernameText.setVisibility(View.GONE);
        Fragment accountInfoFragment = new AccountInfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, accountInfoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openLoginFragment() {
        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        userImage.setVisibility(View.GONE);
        usernameText.setVisibility(View.GONE);
        Fragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openDonateFragment() {
        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        userImage.setVisibility(View.GONE);
        usernameText.setVisibility(View.GONE);
        Fragment donateFragment = new DonateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, donateFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openLanguageFragment() {
        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        userImage.setVisibility(View.GONE);
        usernameText.setVisibility(View.GONE);
        Fragment languageFragment = new LanguageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, languageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("username", "Guest");
        editor.apply();

        loginButtonText.setText("Login");
        usernameText.setText("Guest");
        loginButtonText.setOnClickListener(view -> openLoginFragment());

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.close_more_button) {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
