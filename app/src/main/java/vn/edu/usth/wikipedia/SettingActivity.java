package vn.edu.usth.wikipedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.wikipedia.fragments.DonateFragment;
import vn.edu.usth.wikipedia.fragments.LanguageFragment;
import vn.edu.usth.wikipedia.fragments.LoginFragment;

public class SettingActivity extends AppCompatActivity {

    Button loginButtonText, donateButtonText, languageButtonText;
    ImageButton loginButton, donateButton, languageButton, closeButton;
    RelativeLayout settingBar;
    ImageView usthImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        loginButton = findViewById(R.id.login_button);
        donateButton = findViewById(R.id.donate_button);
        languageButton = findViewById(R.id.language_button);
        loginButtonText = findViewById(R.id.login_button_text);
        donateButtonText = findViewById(R.id.donate_button_text);
        languageButtonText = findViewById(R.id.language_button_text);
        closeButton = findViewById(R.id.close_more_button);
        settingBar = findViewById(R.id.setting_bar);
        usthImage = findViewById(R.id.usth_logo);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginFragment();
            }
        });

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDonateFragment();
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLanguageFragment();
            }
        });

        loginButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginFragment();
            }
        });

        donateButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDonateFragment();
            }
        });

        languageButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLanguageFragment();
            }
        });
    }

    public void openLoginFragment() {

        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        Fragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openDonateFragment() {

        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        Fragment donanteFragment = new DonateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, donanteFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openLanguageFragment() {

        loginButton.setVisibility(View.GONE);
        donateButton.setVisibility(View.GONE);
        languageButton.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        donateButtonText.setVisibility(View.GONE);
        languageButtonText.setVisibility(View.GONE);
        settingBar.setVisibility(View.GONE);
        usthImage.setVisibility(View.GONE);
        Fragment languageFragment = new LanguageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, languageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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