package vn.edu.usth.wikipedia.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.R;

public class LanguageFragment extends Fragment {

    private Spinner languageSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the spinner and button
        languageSpinner = view.findViewById(R.id.language_spinner);
        Button saveLanguageButton = view.findViewById(R.id.save_language_button);

        // Set up the spinner with language options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Set up click listener for the save button
        saveLanguageButton.setOnClickListener(v -> saveLanguagePreference());
    }

    private void saveLanguagePreference() {
        // Save the selected language to SharedPreferences
        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        String languageCode = getLanguageCodeFromLanguage(selectedLanguage);

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language_code", languageCode);
        editor.apply();

        handleNavigationAfterLanguageChange();
    }

    private void handleNavigationAfterLanguageChange() {
        // Determine the current fragment and navigate accordingly
        Fragment previousFragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (previousFragment instanceof ArticleFragment) {
            String currentArticleUrl = ((ArticleFragment) previousFragment).getArticleUrl();
            loadArticleInSelectedLanguage(currentArticleUrl);
        } else {
            navigateToHomepage();
        }
    }

    private void loadArticleInSelectedLanguage(String url) {
        // Convert the URL to the new language and load the article
        String newUrl = convertUrlToNewLanguage(url);

        if (newUrl == null) {
            Toast.makeText(getContext(), "This article is not available in your language", Toast.LENGTH_SHORT).show();
            navigateToHomepage();
        } else {
            // Create and load the ArticleFragment with the new URL
            ArticleFragment newArticleFragment = ArticleFragment.newInstance("Article Title", newUrl); // Replace "Article Title" with actual title if available
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newArticleFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void navigateToHomepage() {
        // Navigate back to the homepage (assuming SearchFragment is the homepage)
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchFragment())
                .commit();
    }

    private String convertUrlToNewLanguage(String url) {
        // Convert the URL to reflect the selected language
        String languageCode = getLanguageCodeFromLanguage(languageSpinner.getSelectedItem().toString());
        String newUrl = url.replace("en", languageCode); // Replace "en" with the language code

        // Implement actual URL verification logic if necessary
        return newUrl; // Return null if the article is not available in the new language
    }

    private String getLanguageCodeFromLanguage(String language) {
        // Map language names to their corresponding language codes
        switch (language) {
            case "Tiếng Việt": return "vi";
            case "Français": return "fr";
            case "Deutsch": return "de";
            case "Español": return "es";
            case "日本語": return "ja";
            case "中文": return "zh";
            case "한국어": return "ko";
            case "Italiano": return "it";
            case "Português": return "pt";
            case "Русский": return "ru";
            case "عربى": return "ar";
            case "Türkçe": return "tr";
            case "Polski": return "pl";
            case "ไทย": return "th";
            case "Swedish": return "sv";
            case "Danish": return "da";
            case "Norsk": return "no";
            case "Finnish": return "fi";
            case "Hrvatski": return "hr";
            case "Slovenský": return "sk";
            case "Magyar": return "hu";
            case "English": return "en";
            default: return "en"; // Default to English if not found
        }
    }
}
