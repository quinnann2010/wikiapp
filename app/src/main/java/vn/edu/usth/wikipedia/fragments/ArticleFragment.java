package vn.edu.usth.wikipedia.fragments;

import android.graphics.Bitmap;
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

import vn.edu.usth.wikipedia.managers.BookmarksManager;
import vn.edu.usth.wikipedia.managers.HistoryManager;
import vn.edu.usth.wikipedia.R;

public class ArticleFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_URL = "url";

    private String articleTitle;
    private String articleUrl;
    private WebView webView;
    private ProgressBar progressBar;
    private BookmarksManager bookmarksManager;
    private HistoryManager historyManager;

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
        }

        webView = view.findViewById(R.id.article_webview);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        progressBar = view.findViewById(R.id.progress_bar);

        bookmarksManager = new BookmarksManager(requireContext());
        historyManager = HistoryManager.getInstance(requireContext());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(articleUrl);

        // Add the current article URL to history
        historyManager.addToHistory(articleUrl);



        if (articleUrl != null) {
            webView.loadUrl(articleUrl);
        } else {
            Toast.makeText(getContext(), "Cannot load URL", Toast.LENGTH_SHORT).show();
        }

        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        updateBookmarkButtonState(bookmarkButton);

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
    }

    private void updateBookmarkButtonState(ImageButton bookmarkButton) {
        if (bookmarksManager.isBookmarked(articleUrl)) {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
