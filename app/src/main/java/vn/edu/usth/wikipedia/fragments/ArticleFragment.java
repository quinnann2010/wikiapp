package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import vn.edu.usth.wikipedia.R;

public class ArticleFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_URL = "url";
    private static final String PREFS_NAME = "bookmarksPrefs";
    private static final String BOOKMARKS_KEY = "bookmarks";
    private String articleTitle;
    private String articleUrl;
    private WebView webView;
    private ProgressBar progressBar;
    private Set<String> bookmarksSet; 

    public static ArticleFragment newInstance(String title, String url) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();

        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            articleTitle = getArguments().getString(ARG_TITLE);
            articleUrl = getArguments().getString(ARG_URL);

            try {
                articleUrl = URLDecoder.decode(articleUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("ArticleFragment", "Loading URL: " + articleUrl);

            addToHistory(articleUrl);
        }

        webView = view.findViewById(R.id.article_webview);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        progressBar = view.findViewById(R.id.progress_bar);

        bookmarksSet = new HashSet<>();
        loadBookmarks();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(articleUrl);

        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        updateBookmarkButtonState(bookmarkButton);

        bookmarkButton.setOnClickListener(v -> {
            if (isBookmarked(articleUrl)) {
                removeBookmark(articleUrl);
                Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
            } else {
                addBookmark(articleUrl);
                Toast.makeText(getContext(), "Added to bookmarks", Toast.LENGTH_SHORT).show();
            }
            updateBookmarkButtonState(bookmarkButton);
        });
    }

    private void addToHistory(String url) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_history", Context.MODE_PRIVATE);
        Set<String> history = new HashSet<>(prefs.getStringSet("history", new HashSet<>()));
        history.add(url);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("history", history);
        editor.apply();
    }


    // Method to check if the article URL is bookmarked
    private boolean isBookmarked(String url) {
        return bookmarksSet.contains(url);
    }

    // Method to add the article URL to the bookmark set
    private void addBookmark(String url) {
        bookmarksSet.add(url);
        saveBookmarks(); 
    }

    // Method to remove the article URL from the bookmark set
    private void removeBookmark(String url) {
        bookmarksSet.remove(url);
        saveBookmarks(); 
    }

    // Method to update the bookmark button based on whether the article is bookmarked
    private void updateBookmarkButtonState(ImageButton bookmarkButton) {
        if (isBookmarked(articleUrl)) {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    // Load bookmarks
    private void loadBookmarks() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        bookmarksSet = sharedPreferences.getStringSet(BOOKMARKS_KEY, new HashSet<>());
    }

    // Save bookmarks to persistent storage
    private void saveBookmarks() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(BOOKMARKS_KEY, bookmarksSet);
        editor.apply();
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
