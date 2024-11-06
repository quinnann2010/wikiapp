package vn.edu.usth.wikipedia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.wikipedia.models.Article;
import vn.edu.usth.wikipedia.R;

// Adapter class for displaying saved articles in a RecyclerView
public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.ViewHolder> {

    private List<Article> savedArticles; // List of saved articles
    private OnArticleClickListener onArticleClickListener; // Listener for article clicks
    private OnDeleteClickListener onDeleteClickListener; // Listener for delete button clicks

    // Interface for handling article clicks
    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    // Interface for handling delete button clicks
    public interface OnDeleteClickListener {
        void onDeleteClick(Article article);
    }

    // Constructor to initialize the adapter with data and listeners
    public SavedArticleAdapter(List<Article> savedArticles, OnArticleClickListener onArticleClickListener, OnDeleteClickListener onDeleteClickListener) {
        this.savedArticles = savedArticles;
        this.onArticleClickListener = onArticleClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the article at the current position
        Article article = savedArticles.get(position);

        // Set the article title
        holder.titleTextView.setText(article.getTitle());

        // Set up click listener for the article item
        holder.itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));

        // Set up click listener for the delete button
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(article));
    }

    @Override
    public int getItemCount() {
        // Return the number of saved articles
        return savedArticles.size();
    }

    // ViewHolder class to hold views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        Button deleteButton; // Button to delete the article

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            titleTextView = itemView.findViewById(R.id.article_title);
            deleteButton = itemView.findViewById(R.id.delete_button); // Connect with layout
        }
    }
}
