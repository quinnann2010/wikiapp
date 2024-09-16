package vn.edu.usth.wikipedia.fragments;

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
import androidx.fragment.app.FragmentManager;

import java.util.List;

import vn.edu.usth.wikipedia.adapters.HistoryAdapter;
import vn.edu.usth.wikipedia.adapters.HistoryManager;
import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;

public class HistoryFragment extends Fragment {

    private ListView historyListView;
    private TextView emptyView;
    private HistoryAdapter historyAdapter;
    private List<String> history;
    private HistoryManager historyManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyListView = view.findViewById(R.id.history_list);
        emptyView = view.findViewById(R.id.empty_view);
        Button clearHistoryButton = view.findViewById(R.id.clear_history_button);
        Button backToHomeButton = view.findViewById(R.id.back_to_home_button);

        historyManager = HistoryManager.getInstance(requireContext()); // Use singleton instance

        history = historyManager.getHistory(); // Retrieve history from HistoryManager
        historyAdapter = new HistoryAdapter(getContext(), history, historyItem -> {
            // Delete a history item when the delete button is pressed
            historyManager.removeFromHistory(historyItem);
            history.remove(historyItem);
            historyAdapter.notifyDataSetChanged();  // Update the UI
            checkIfEmpty();  // Check and update "Empty" message
            Toast.makeText(getContext(), "History item deleted", Toast.LENGTH_SHORT).show();
        });
        historyListView.setAdapter(historyAdapter);

        // Check if there are no items in the list and update the UI
        checkIfEmpty();

        // Clear all history
        clearHistoryButton.setOnClickListener(v -> {
            historyManager.clearHistory(); // Clear history via HistoryManager
            history.clear();
            historyAdapter.notifyDataSetChanged();
            checkIfEmpty();  // Check and update "Empty" message
            Toast.makeText(getContext(), "History cleared", Toast.LENGTH_SHORT).show();
        });

        // Return to the main screen
        backToHomeButton.setOnClickListener(v -> {
            // Pop back stack
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Replace with default fragment (e.g., SearchFragment) if needed
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();


        });

        // Handle item click in the history list
        historyListView.setOnItemClickListener((parent, view1, position, id) -> {
            String url = history.get(position);
            openArticle(url);
        });
    }

    private void openArticle(String url) {
        // Create a new ArticleFragment and pass the URL
        ArticleFragment articleFragment = ArticleFragment.newInstance("Article Title", url);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, articleFragment)
                .addToBackStack(null)
                .commit();
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
