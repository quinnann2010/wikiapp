package vn.edu.usth.wikipedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.wikipedia.R;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private final Context context;
    private final List<String> bookmarks;
    private final BookmarkDeleteListener deleteListener;
    private OnItemClickListener onItemClickListener;

    public interface BookmarkDeleteListener {
        void onDelete(String bookmark);
    }

    public interface OnItemClickListener {
        void onItemClick(String bookmark);
    }

    public BookmarkAdapter(Context context, List<String> bookmarks, BookmarkDeleteListener deleteListener) {
        this.context = context;
        this.bookmarks = bookmarks;
        this.deleteListener = deleteListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String bookmark = bookmarks.get(position);
        holder.titleTextView.setText(bookmark);

        // Xử lý sự kiện xóa bookmark
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(bookmark);
                bookmarks.remove(position); // Xóa bookmark khỏi danh sách
                notifyItemRemoved(position); // Cập nhật RecyclerView
                notifyItemRangeChanged(position, bookmarks.size()); // Cập nhật các vị trí còn lại
            }
        });

        // Xử lý sự kiện nhấp vào bookmark
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(bookmark);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookmark_title);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
