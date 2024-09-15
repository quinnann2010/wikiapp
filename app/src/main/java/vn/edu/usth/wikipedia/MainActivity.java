package vn.edu.usth.wikipedia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.wikipedia.fragments.ArticleFragment;
import vn.edu.usth.wikipedia.fragments.BookmarkFragment;
import vn.edu.usth.wikipedia.fragments.DonateFragment;
import vn.edu.usth.wikipedia.fragments.ExploreFragment;
import vn.edu.usth.wikipedia.fragments.HistoryFragment;
import vn.edu.usth.wikipedia.fragments.LanguageFragment;
import vn.edu.usth.wikipedia.fragments.LoginFragment;
import vn.edu.usth.wikipedia.fragments.SaveFragment;
import vn.edu.usth.wikipedia.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Button moreButton, exploreButton, saveButton, historyButton, searchButton, userButton, loginButton, logoutButton;
    private LinearLayout hiddenButtonsLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Reference buttons
        moreButton = findViewById(R.id.more_button);
        searchButton = findViewById(R.id.search_button);
        exploreButton = findViewById(R.id.explore_button);
        saveButton = findViewById(R.id.save_button);
        historyButton = findViewById(R.id.history_button);
        userButton = findViewById(R.id.user_button); // User button
        loginButton = findViewById(R.id.login_button); // Login button
        logoutButton = findViewById(R.id.logout_button); // Logout button
        hiddenButtonsLayout = findViewById(R.id.hidden_buttons);

        // Load the default fragment (e.g., SearchFragment)
        loadFragment(new SearchFragment());

        // Initialize button click listeners
        moreButton.setOnClickListener(v -> toggleHiddenButtons(true));
        findViewById(R.id.close_more_button).setOnClickListener(v -> toggleHiddenButtons(false));
        exploreButton.setOnClickListener(v -> loadFragment(new ExploreFragment()));
        saveButton.setOnClickListener(v -> loadFragment(new SaveFragment()));
        historyButton.setOnClickListener(v -> loadFragment(new HistoryFragment()));
        searchButton.setOnClickListener(v -> loadFragment(new SearchFragment()));
        loginButton.setOnClickListener(v -> loadFragment(new LoginFragment()));
        findViewById(R.id.donate_button).setOnClickListener(v -> loadFragment(new DonateFragment()));
        findViewById(R.id.language_button).setOnClickListener(v -> loadFragment(new LanguageFragment()));
        findViewById(R.id.bookmark_button).setOnClickListener(v -> loadFragment(new BookmarkFragment()));
        logoutButton.setOnClickListener(v -> handleLogout());
    }

    private void toggleHiddenButtons(boolean show) {
        // Show or hide the container for hidden buttons
        hiddenButtonsLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        moreButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void handleLogout() {
        // Handle logout logic
        userButton.setVisibility(View.GONE); // Hide user button
        logoutButton.setVisibility(View.GONE); // Hide logout button
        loginButton.setVisibility(View.VISIBLE); // Show login button
    }

    private void loadFragment(Fragment fragment) {
        // Replace the current fragment with the new one and add transaction to back stack
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // Manage visibility of the 'More' button based on the fragment type
        if (fragment instanceof SearchFragment) {
            moreButton.setVisibility(View.VISIBLE);
        } else {
            moreButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        // Handle back press based on the visibility of hidden buttons
        if (hiddenButtonsLayout.getVisibility() == View.VISIBLE) {
            toggleHiddenButtons(false);
        } else {
            super.onBackPressed();
        }
    }

    public void showArticle(String title, String url) {
        // Create and load an ArticleFragment with the given title and URL
        Fragment articleFragment = ArticleFragment.newInstance(title, url);
        loadFragment(articleFragment);
    }

    public void showInitialButtons() {
        // Show the initial buttons (e.g., 'More' and 'Search')
        moreButton.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
    }

    public void updateButtonVisibility(boolean isLoggedIn) {
        // Update button visibility based on login state
        if (isLoggedIn) {
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            userButton.setVisibility(View.VISIBLE);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            userButton.setVisibility(View.GONE);
        }
    }
}
