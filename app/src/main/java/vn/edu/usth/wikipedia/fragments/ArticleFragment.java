package vn.edu.usth.wikipedia.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.usth.wikipedia.adapters.BookmarksManager;
import vn.edu.usth.wikipedia.adapters.HistoryManager;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.adapters.SavedArticlesManager;

/**
 * Fragment to display an article using a WebView and provide functionality for bookmarking,
 * saving, and navigating back.
 */
public class ArticleFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_URL = "url";

    private String articleTitle;
    private String articleUrl;
    private WebView webView;
    private BookmarksManager bookmarksManager;
    private HistoryManager historyManager;
    private SavedArticlesManager savedArticlesManager;

    /**
     * Creates a new instance of ArticleFragment with the specified title and URL.
     *
     * @param title The title of the article.
     * @param url The URL of the article.
     * @return A new instance of ArticleFragment.
     */
    public static ArticleFragment newInstance(String title, String url) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve arguments passed to the fragment
        if (getArguments() != null) {
            articleTitle = getArguments().getString(ARG_TITLE);
            articleUrl = getArguments().getString(ARG_URL);
        }

        // Initialize UI components
        webView = view.findViewById(R.id.article_webview);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        ImageButton saveButton = view.findViewById(R.id.save_button);

        // Initialize managers for bookmarks, history, and saved articles
        bookmarksManager = new BookmarksManager(requireContext());
        historyManager = HistoryManager.getInstance(requireContext());
        savedArticlesManager = new SavedArticlesManager(requireContext());

        // Configure WebView settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(articleUrl);

        // Add the current article URL to history
        historyManager.addToHistory(articleUrl);

        // Set click listener for the back button
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Update the bookmark button state based on whether the article is bookmarked
        updateBookmarkButtonState(bookmarkButton);

        // Set click listener for the bookmark button
        bookmarkButton.setOnClickListener(v -> {
            if (bookmarksManager.isBookmarked(articleUrl)) {
                bookmarksManager.removeBookmark(articleUrl);
                Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
            } else {
                bookmarksManager.addBookmark(articleUrl);
                Toast.makeText(getContext(), "Added to bookmarks", Toast.LENGTH_SHORT).show();
            }
            updateBookmarkButtonState(bookmarkButton);
        });

        // Set click listener for the save button
        saveButton.setOnClickListener(v -> showSaveDialog());
    }

    /**
     * Updates the bookmark button's icon based on whether the article is bookmarked.
     *
     * @param bookmarkButton The ImageButton for bookmarking.
     */
    private void updateBookmarkButtonState(ImageButton bookmarkButton) {
        if (bookmarksManager.isBookmarked(articleUrl)) {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    /**
     * Shows a dialog allowing the user to enter a title and save the article.
     */
    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Save Article");

        final EditText input = new EditText(getContext());
        input.setHint("Enter title");
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String customTitle = input.getText().toString().trim();
            if (customTitle.isEmpty()) {
                customTitle = "No Title";
            }
            saveArticle(customTitle);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Saves or removes the article from saved articles based on its current state.
     *
     * @param title The title for saving the article.
     */
    private void saveArticle(String title) {
        if (savedArticlesManager.isArticleSaved(articleUrl)) {
            savedArticlesManager.removeArticle(articleUrl);
            Toast.makeText(getContext(), "Removed from saved articles", Toast.LENGTH_SHORT).show();
        } else {
            webView.saveWebArchive(savedArticlesManager.getArticleFilePath(articleUrl));
            savedArticlesManager.addArticle(articleUrl, title);
            Toast.makeText(getContext(), "Saved for offline reading", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets the URL of the current article.
     *
     * @return The URL of the article.
     */
    public String getArticleUrl() {
        return articleUrl;
    }
}
