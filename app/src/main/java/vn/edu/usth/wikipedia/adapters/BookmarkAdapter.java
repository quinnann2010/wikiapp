package vn.edu.usth.wikipedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.edu.usth.wikipedia.R;

/**
 * Adapter for displaying a list of bookmarks in a ListView with the option to delete bookmarks.
 */
public class BookmarkAdapter extends ArrayAdapter<String> {

    private final OnDeleteClickListener onDeleteClickListener; // Listener for delete button clicks

    /**
     * Interface for handling delete button clicks.
     */
    public interface OnDeleteClickListener {
        void onDeleteClick(String bookmark);
    }

    /**
     * Constructor for BookmarkAdapter.
     *
     * @param context The application context.
     * @param bookmarks The list of bookmarks to display.
     * @param onDeleteClickListener The listener to handle delete button clicks.
     */
    public BookmarkAdapter(Context context, List<String> bookmarks, OnDeleteClickListener onDeleteClickListener) {
        super(context, 0, bookmarks); // Call the parent constructor with the context, a resource ID of 0, and the list of bookmarks
        this.onDeleteClickListener = onDeleteClickListener; // Initialize the delete click listener
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the item view if it doesn't already exist
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bookmark, parent, false);
        }

        // Find views in the item layout
        TextView bookmarkTitle = convertView.findViewById(R.id.bookmark_title); // TextView to display the bookmark title
        Button deleteButton = convertView.findViewById(R.id.delete_button); // Button to delete the bookmark

        // Get the bookmark for the current position
        String bookmark = getItem(position);
        bookmarkTitle.setText(bookmark); // Set the bookmark title in the TextView

        // Set an OnClickListener for the delete button
        deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(bookmark); // Call the delete listener when the button is clicked
            }
        });

        return convertView; // Return the item view
    }
}
