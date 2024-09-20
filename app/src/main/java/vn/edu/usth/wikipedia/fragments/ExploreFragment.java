package vn.edu.usth.wikipedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import vn.edu.usth.wikipedia.models.Article;
import vn.edu.usth.wikipedia.adapters.ArticleAdapter;
import vn.edu.usth.wikipedia.managers.ArticleManager;
import vn.edu.usth.wikipedia.R;

/**
 * Fragment for exploring popular articles.
 */
public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articles;
    private ArticleManager articleManager; // Manage fetching articles


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false); // Inflate the layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set up layout manager

        articleManager = new ArticleManager(requireContext());
        articles = articleManager.getPopularArticles(); // Fetch popular articles

        articleAdapter = new ArticleAdapter(articles, article -> {
            // Handle article click
            openArticle(article);
        });
        recyclerView.setAdapter(articleAdapter); // Set up adapter

        Button showButton = view.findViewById(R.id.show_vu);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


    }

    private void openArticle(Article article) {
        // Open the selected article in a new fragment
        recyclerView.setVisibility(View.GONE);
                Fragment articleFragment = ArticleFragment.newInstance(article.getTitle(), article.getUrl());
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, articleFragment)
                .addToBackStack(null)
                .commit();
    }
}
