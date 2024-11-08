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
            url = URLEncoder.encode(url, "UTF-8"); // Mã hóa URL
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
        return inflater.inflate(R.layout.fragment_article, container, false); // Inflate layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy dữ liệu từ arguments
        if (getArguments() != null) {
            articleTitle = getArguments().getString(ARG_TITLE);
            articleUrl = getArguments().getString(ARG_URL);

            try {
                articleUrl = URLDecoder.decode(articleUrl, "UTF-8"); // Giải mã URL
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("ArticleFragment", "Loading URL: " + articleUrl);
        }

        webView = view.findViewById(R.id.article_webview);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton bookmarkButton = view.findViewById(R.id.bookmark_button);
        progressBar = view.findViewById(R.id.progress_bar);

        bookmarksManager = new BookmarksManager(requireContext()); // Khởi tạo quản lý bookmark
        historyManager = HistoryManager.getInstance(requireContext()); // Khởi tạo quản lý lịch sử

        webView.getSettings().setJavaScriptEnabled(true); // Bật JavaScript
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE); // Hiện progress bar khi trang bắt đầu tải
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE); // Ẩn progress bar khi trang đã tải xong
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(getContext(), "Failed to load URL", Toast.LENGTH_SHORT).show(); // Thông báo lỗi tải trang
            }
        });

        // Thêm URL bài viết vào lịch sử
        historyManager.addToHistory(articleUrl);

        if (articleUrl != null) {
            webView.loadUrl(articleUrl); // Tải URL bài viết
        } else {
            Toast.makeText(getContext(), "Cannot load URL", Toast.LENGTH_SHORT).show();
        }

        // Quay lại trang trước
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        updateBookmarkButtonState(bookmarkButton); // Cập nhật trạng thái nút bookmark

        bookmarkButton.setOnClickListener(v -> {
            if (bookmarksManager.isBookmarked(articleUrl)) {
                bookmarksManager.removeBookmark(articleUrl); // Xóa bookmark
                Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
            } else {
                bookmarksManager.addBookmark(articleUrl); // Thêm bookmark
                Toast.makeText(getContext(), "Added to bookmarks", Toast.LENGTH_SHORT).show();
            }
            updateBookmarkButtonState(bookmarkButton); // Cập nhật trạng thái nút bookmark
        });
    }

    private void updateBookmarkButtonState(ImageButton bookmarkButton) {
        // Cập nhật hình ảnh của nút bookmark dựa trên trạng thái bookmark
        if (bookmarksManager.isBookmarked(articleUrl)) {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    public String getArticleUrl() {
        return articleUrl; // Trả về URL bài viết
    }
}
