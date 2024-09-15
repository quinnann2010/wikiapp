package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.wikipedia.models.Article;
import vn.edu.usth.wikipedia.MainActivity;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.adapters.SavedArticleAdapter;
import vn.edu.usth.wikipedia.adapters.SavedArticlesManager;

public class SaveFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private SavedArticleAdapter adapter;
    private SavedArticlesManager savedArticlesManager;
    private List<Article> savedArticles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        Button clearAllButton = view.findViewById(R.id.clear_all_button);
        Button backToHomeButton = view.findViewById(R.id.back_to_home_button);

        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is not found in the layout file.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedArticlesManager = new SavedArticlesManager(requireContext());
        savedArticles = getSavedArticles();

        adapter = new SavedArticleAdapter(savedArticles, article -> {
            // Handle article click
            openArticle(article.getUrl());
        }, article -> {
            // Handle delete button click
            deleteArticle(article);
        });

        recyclerView.setAdapter(adapter);

        // Check if the list is empty and update UI
        checkIfEmpty();

        // Clear all saved articles
        clearAllButton.setOnClickListener(v -> {
            savedArticlesManager.clearSavedArticles();
            savedArticles.clear();
            adapter.notifyDataSetChanged();
            checkIfEmpty();  // Check and update "Empty" view
        });

        // Go back to the home screen
        backToHomeButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();

            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.showInitialButtons();
        });
    }

    private List<Article> getSavedArticles() {
        List<Article> articles = new ArrayList<>();
        File[] files = savedArticlesManager.getArticlesDir().listFiles();
        if (files != null) {
            for (File file : files) {
                String url = file.getName().replace(".mht", "");
                String title = savedArticlesManager.getArticleTitle(url);
                articles.add(new Article(title, url));
            }
        }
        return articles;
    }

    private void openArticle(String url) {
        ArticleFragment articleFragment = ArticleFragment.newInstance("Article Title", url);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, articleFragment)
                .addToBackStack(null)
                .commit();
    }

    private void deleteArticle(Article article) {
        savedArticlesManager.removeArticle(article.getUrl());
        savedArticles.remove(article); // Remove from display list
        adapter.notifyDataSetChanged(); // Update UI
        checkIfEmpty();  // Check and update "Empty" view
    }

    private void checkIfEmpty() {
        if (savedArticles.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
