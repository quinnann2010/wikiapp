package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.wikipedia.adapters.BookmarkAdapter;
import vn.edu.usth.wikipedia.adapters.BookmarksManager;
import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;

/**
 * Fragment to display and manage a list of bookmarks.
 */
public class BookmarkFragment extends Fragment {

    private ListView bookmarkListView; // ListView to display bookmarks
    private TextView emptyView; // TextView to display when no bookmarks are present
    private BookmarkAdapter bookmarkAdapter; // Adapter to manage the bookmarks list
    private List<String> bookmarks; // List of bookmarks
    private BookmarksManager bookmarksManager; // Manager for handling bookmarks

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        bookmarkListView = view.findViewById(R.id.bookmark_list);
        emptyView = view.findViewById(R.id.empty_view);
        Button clearBookmarksButton = view.findViewById(R.id.clear_bookmarks_button);
        Button backToHomeButton = view.findViewById(R.id.back_to_home_button);

        // Initialize bookmarks manager and list
        bookmarksManager = new BookmarksManager(requireContext());
        bookmarks = new ArrayList<>(bookmarksManager.getBookmarks());

        // Set up the adapter with a delete click listener
        bookmarkAdapter = new BookmarkAdapter(getContext(), bookmarks, bookmark -> {
            // Handle delete action
            bookmarksManager.removeBookmark(bookmark); // Remove from manager
            bookmarks.remove(bookmark); // Remove from list
            bookmarkAdapter.notifyDataSetChanged(); // Update adapter
            checkIfEmpty(); // Update empty view
            Toast.makeText(getContext(), "Bookmark deleted", Toast.LENGTH_SHORT).show();
        });

        // Set the adapter for the ListView
        bookmarkListView.setAdapter(bookmarkAdapter);

        // Check if the list is empty and update the UI accordingly
        checkIfEmpty();

        // Handle item click in the ListView
        bookmarkListView.setOnItemClickListener((AdapterView<?> parent, View itemView, int position, long id) -> {
            String articleUrl = bookmarks.get(position); // Get the article URL
            String articleTitle = getArticleTitleFromUrl(articleUrl); // Extract the article title

            // Show the article in MainActivity
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showArticle(articleTitle, articleUrl);
            }
        });

        // Handle click for the "Clear Bookmarks" button
        clearBookmarksButton.setOnClickListener(v -> {
            bookmarksManager.clearBookmarks(); // Clear all bookmarks
            bookmarks.clear(); // Clear the list
            bookmarkAdapter.notifyDataSetChanged(); // Update adapter
            checkIfEmpty(); // Update empty view
            Toast.makeText(getContext(), "Bookmarks cleared", Toast.LENGTH_SHORT).show();
        });

        // Handle click for the "Back to Home" button
        backToHomeButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            // Pop all fragments from the back stack
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Replace current fragment with SearchFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();

            // Show initial buttons in MainActivity
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showInitialButtons();
        });
    }

    /**
     * Helper method to get the article title from its URL.
     *
     * @param url The article URL.
     * @return The article title.
     */
    private String getArticleTitleFromUrl(String url) {
        if (url.contains("/wiki/")) {
            return url.substring(url.lastIndexOf("/wiki/") + 6).replace("_", " ");
        }
        return url;
    }

    /**
     * Helper method to check if the bookmarks list is empty and update the UI accordingly.
     */
    private void checkIfEmpty() {
        if (bookmarks.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE); // Show empty view
            bookmarkListView.setVisibility(View.GONE); // Hide ListView
        } else {
            emptyView.setVisibility(View.GONE); // Hide empty view
            bookmarkListView.setVisibility(View.VISIBLE); // Show ListView
        }
    }
}
