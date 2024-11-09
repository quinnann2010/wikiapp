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

public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.ViewHolder> {

    private List<Article> savedArticles;
    private OnArticleClickListener onArticleClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Article article);
    }

    public SavedArticleAdapter(List<Article> savedArticles, OnArticleClickListener onArticleClickListener, OnDeleteClickListener onDeleteClickListener) {
        this.savedArticles = savedArticles;
        this.onArticleClickListener = onArticleClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = savedArticles.get(position);

        holder.titleTextView.setText(article.getTitle());

        holder.itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));

        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(article));
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
