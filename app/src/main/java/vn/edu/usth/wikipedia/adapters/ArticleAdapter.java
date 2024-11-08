package vn.edu.usth.wikipedia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.wikipedia.models.Article;
import vn.edu.usth.wikipedia.R;

/**
 * Adapter for displaying a list of Articles in a RecyclerView.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    // List of articles to be displayed
    private final List<Article> articles;
    // Listener for article click events
    private final OnArticleClickListener onArticleClickListener;

    /**
     * Interface for handling article click events.
     */
    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    /**
     * Constructor to initialize the ArticleAdapter with a list of articles and a click listener.
     *
     * @param articles The list of articles to display.
     * @param onArticleClickListener The listener to handle article click events.
     */
    public ArticleAdapter(List<Article> articles, OnArticleClickListener onArticleClickListener) {
        this.articles = articles;
        this.onArticleClickListener = onArticleClickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item view for each article
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // Bind article data to the view holder
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        // Set click listener for the item view
        holder.itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));
    }

    @Override
    public int getItemCount() {
        // Return the total number of articles
        return articles.size();
    }

    /**
     * ViewHolder class for holding the view elements for an article.
     */
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        // TextView for displaying the article title
        TextView titleTextView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextView for article title
            titleTextView = itemView.findViewById(R.id.article_title);
        }
    }
}
