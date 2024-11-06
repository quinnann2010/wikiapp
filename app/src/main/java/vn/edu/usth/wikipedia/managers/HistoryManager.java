package vn.edu.usth.wikipedia.managers;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryManager {

    private static final String PREFS_NAME = "history_prefs";
    private static final String KEY_HISTORY = "history";
    private static HistoryManager instance;
    private SharedPreferences sharedPreferences;

    private HistoryManager(Context context) {
        // Initialize SharedPreferences
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized HistoryManager getInstance(Context context) {
        // Singleton pattern to get a single instance of HistoryManager
        if (instance == null) {
            instance = new HistoryManager(context.getApplicationContext());
        }
        return instance;
    }

    public void addToHistory(String articleUrl) {
        // Add an article URL to the history if it's not already present
        List<String> history = getHistory();
        if (!history.contains(articleUrl)) {
            history.add(articleUrl);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_HISTORY, String.join(",", history));
            editor.apply();
        }
    }

    public List<String> getHistory() {
        // Retrieve the list of article URLs from history
        String historyString = sharedPreferences.getString(KEY_HISTORY, "");
        if (historyString.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, historyString.split(","));
        return historyList;
    }

    public void removeFromHistory(String articleUrl) {
        // Remove a specific article URL from the history
        List<String> history = getHistory();
        history.remove(articleUrl);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_HISTORY, String.join(",", history));
        editor.apply();
    }

    public void clearHistory() {
        // Clear all history entries
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_HISTORY);
        editor.apply();
    }
}
