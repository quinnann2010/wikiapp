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

public class HistoryAdapter extends ArrayAdapter<String> {

    private final OnDeleteClickListener onDeleteClickListener;

    // Interface for handling delete events
    public interface OnDeleteClickListener {
        void onDeleteClick(String historyItem);
    }

    public HistoryAdapter(Context context, List<String> history, OnDeleteClickListener onDeleteClickListener) {
        super(context, 0, history);
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history, parent, false);
        }

        TextView historyTitle = convertView.findViewById(R.id.history_title);
        Button deleteButton = convertView.findViewById(R.id.delete_button); // Add delete button

        String historyItem = getItem(position);
        historyTitle.setText(historyItem);

        // Handle the click event when the delete button is pressed
        deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(historyItem);  // Call listener when delete is pressed
            }
        });

        return convertView;
    }
}
