package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.adapters.BookmarkAdapter;

public class BookmarkFragment extends Fragment implements BookmarkAdapter.BookmarkDeleteListener {

    private static final String PREFS_NAME = "bookmarksPrefs";
    private static final String BOOKMARKS_KEY = "bookmarks";
    private List<String> bookmarks;
    private RecyclerView bookmarkRecyclerView;
    private TextView emptyView;
    private BookmarkAdapter bookmarkAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookmarkRecyclerView = view.findViewById(R.id.bookmark_list);
        emptyView = view.findViewById(R.id.empty_view);
        Button clearButton = view.findViewById(R.id.clear_bookmarks_button);

        bookmarks = new ArrayList<>();
        loadBookmarks();

        bookmarkAdapter = new BookmarkAdapter(getContext(), bookmarks, this);
        bookmarkRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkRecyclerView.setAdapter(bookmarkAdapter);

        checkIfEmpty();

        // Thiết lập sự kiện nhấp vào bookmark
        bookmarkAdapter.setOnItemClickListener(bookmark -> {
            openArticleFragment(bookmark); // Mở ArticleFragment khi nhấn vào bookmark
        });

        clearButton.setOnClickListener(v -> {
            if (!bookmarks.isEmpty()) {
                bookmarks.clear();
                saveBookmarks();
                bookmarkAdapter.notifyDataSetChanged();
                checkIfEmpty();
                Toast.makeText(getContext(), "All bookmarks cleared", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No bookmarks to clear", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookmarks();  // Tải lại bookmark từ SharedPreferences
        bookmarkAdapter.notifyDataSetChanged();  // Cập nhật lại giao diện
        checkIfEmpty();  // Kiểm tra xem danh sách bookmark có trống không
    }

    private void loadBookmarks() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> bookmarkSet = sharedPreferences.getStringSet(BOOKMARKS_KEY, new HashSet<>());
        bookmarks.clear();
        bookmarks.addAll(bookmarkSet);
    }

    private void saveBookmarks() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(BOOKMARKS_KEY, new HashSet<>(bookmarks));
        editor.apply();
    }

    private void checkIfEmpty() {
        if (bookmarks.isEmpty()) {
            bookmarkRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            bookmarkRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    // Mở ArticleFragment với URL của bài viết được nhấn vào
    private void openArticleFragment(String bookmarkUrl) {
        ArticleFragment articleFragment = ArticleFragment.newInstance("Bookmark Article", bookmarkUrl);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, articleFragment);
        transaction.addToBackStack(null); // Cho phép quay lại
        transaction.commit();
    }

    @Override
    public void onDelete(String bookmark) {
        bookmarks.remove(bookmark);
        saveBookmarks();
        bookmarkAdapter.notifyDataSetChanged();
        checkIfEmpty();
        Toast.makeText(getContext(), "Bookmark removed", Toast.LENGTH_SHORT).show();
    }
}
