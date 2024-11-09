package vn.edu.usth.wikipedia.adapters;

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
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized HistoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new HistoryManager(context.getApplicationContext());
        }
        return instance;
    }

    public void addToHistory(String articleUrl) {
        List<String> history = getHistory();
        if (!history.contains(articleUrl)) {
            history.add(articleUrl);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_HISTORY, String.join(",", history));
            editor.apply();
        }
    }

    public List<String> getHistory() {
        String historyString = sharedPreferences.getString(KEY_HISTORY, "");
        if (historyString.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> historyList = new ArrayList<>();
        Collections.addAll(historyList, historyString.split(","));
        return historyList;
    }

    public void removeFromHistory(String articleUrl) {
        List<String> history = getHistory();
        history.remove(articleUrl);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_HISTORY, String.join(",", history));
        editor.apply();
    }

    public void clearHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_HISTORY);
        editor.apply();
    }
}
