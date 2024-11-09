package vn.edu.usth.wikipedia.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Locale;
import vn.edu.usth.wikipedia.MainActivity;

import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.SettingActivity;

public class LanguageFragment extends Fragment {

    private Spinner languageSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        languageSpinner = view.findViewById(R.id.language_spinner);
        Button saveLanguageButton = view.findViewById(R.id.save_language_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        saveLanguageButton.setOnClickListener(v -> saveLanguagePreference());

        ImageButton closeLanguage = view.findViewById(R.id.close_language_button);
        closeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveLanguagePreference() {
        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        String languageCode = getLanguageCodeFromLanguage(selectedLanguage);

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language_code", languageCode);
        editor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        requireActivity().finish();
    }


    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = new Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        requireActivity().recreate();
    }



    private void navigateToHomepage() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchFragment())
                .commit();
    }


    private String getLanguageCodeFromLanguage(String language) {
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
            case "ไทย": return "th";
            case "Danish": return "da";
            case "Norsk": return "no";
            case "Finnish": return "fi";
            case "Hrvatski": return "hr";
            case "Slovenský": return "sk";

            case "English": return "en";
            default: return "en";
        }
    }
}
