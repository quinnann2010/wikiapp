package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.wikipedia.adapters.HistoryAdapter;
import vn.edu.usth.wikipedia.R;

public class HistoryFragment extends Fragment {

    private ListView historyListView;
    private TextView emptyView;
    private HistoryAdapter historyAdapter;
    private List<String> history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHistory();
        checkIfEmpty();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyListView = view.findViewById(R.id.history_list);
        emptyView = view.findViewById(R.id.empty_view);
        Button clearHistoryButton = view.findViewById(R.id.clear_history_button);

        history = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getContext(), history, historyItem -> {
            removeFromHistory(historyItem);
            history.remove(historyItem);
            historyAdapter.notifyDataSetChanged();
            checkIfEmpty();
            Toast.makeText(getContext(), "History item deleted", Toast.LENGTH_SHORT).show();
        });
        historyListView.setAdapter(historyAdapter);

        clearHistoryButton.setOnClickListener(v -> {
            clearHistory();
            history.clear();
            historyAdapter.notifyDataSetChanged();
            checkIfEmpty();
            Toast.makeText(getContext(), "History cleared", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadHistory() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_history", Context.MODE_PRIVATE);
        Set<String> historySet = prefs.getStringSet("history", new HashSet<>());
        history.clear();
        history.addAll(historySet);
        historyAdapter.notifyDataSetChanged();
    }

    private void removeFromHistory(String url) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_history", Context.MODE_PRIVATE);
        Set<String> historySet = new HashSet<>(prefs.getStringSet("history", new HashSet<>()));
        historySet.remove(url);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("history", historySet);
        editor.apply();
    }

    private void clearHistory() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("history").apply();
    }

    private void checkIfEmpty() {
        if (history.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            historyListView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            historyListView.setVisibility(View.VISIBLE);
        }
    }
}
