package vn.edu.usth.wikipedia.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.models.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final List<Article> articles;
    private final OnArticleClickListener onArticleClickListener;

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    public ArticleAdapter(List<Article> articles, OnArticleClickListener onArticleClickListener) {
        this.articles = articles;
        this.onArticleClickListener = onArticleClickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
        holder.articleImage.setImageResource(article.getImageResId()); // Ảnh mẫu, thay thế bằng logic tải ảnh thực tế

        if (!article.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(article.getImageUrl()) // Load the image URL
                    .into(holder.articleImage); // Set it to the ImageView
        } else {
            // Optionally set a placeholder image if the URL is empty
            holder.articleImage.setImageResource(R.drawable.wiki_icon); // Placeholder image resource
        }


        holder.itemView.setOnClickListener(v -> onArticleClickListener.onArticleClick(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView articleImage;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            descriptionTextView = itemView.findViewById(R.id.article_description);
            articleImage = itemView.findViewById(R.id.article_image);
        }
    }
}
