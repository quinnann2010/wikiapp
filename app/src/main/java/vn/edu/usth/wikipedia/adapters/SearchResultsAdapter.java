package vn.edu.usth.wikipedia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import vn.edu.usth.wikipedia.R;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final List<Map<String, String>> searchResults;
    private final OnResultClickListener onResultClickListener;

    public interface OnResultClickListener {
        void onResultClicked(Map<String, String> result);
    }

    public SearchResultsAdapter(List<Map<String, String>> searchResults, OnResultClickListener onResultClickListener) {
        this.searchResults = searchResults;
        this.onResultClickListener = onResultClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, String> result = searchResults.get(position);
        holder.titleTextView.setText(result.get("title"));
        holder.snippetTextView.setText(result.get("snippet"));

        holder.itemView.setOnClickListener(v -> onResultClickListener.onResultClicked(result));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView snippetTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.result_title);
            snippetTextView = itemView.findViewById(R.id.result_snippet);
        }
    }
}
