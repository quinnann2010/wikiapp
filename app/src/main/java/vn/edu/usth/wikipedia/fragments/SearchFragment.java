package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.edu.usth.wikipedia.R;
import vn.edu.usth.wikipedia.adapters.SearchResultsAdapter;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView searchResultsRecyclerView;
    private SearchResultsAdapter adapter;
    private List<Map<String, String>> searchResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchInput = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.search_button);
        searchResultsRecyclerView = view.findViewById(R.id.search_results);

        searchResults = new ArrayList<>();
        adapter = new SearchResultsAdapter(searchResults, result -> {
            String title = result.get("title");
            String url = result.get("url");
            openArticleFragment(title, url); // Open the article on click
        });
        searchResultsRecyclerView.setAdapter(adapter);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        String languageCode = getLanguagePreference();
        String url;
        try {
            // Encode the query to handle spaces and special characters
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            url = "https://" + languageCode + ".wikipedia.org/w/api.php?action=query&list=search&format=json&srsearch="
                    + encodedQuery;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Encoding error", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String responseData = response.body().string();
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
                JsonObject queryObject = jsonObject.getAsJsonObject("query");
                JsonArray searchResultsArray = queryObject.getAsJsonArray("search");

                List<Map<String, String>> results = new ArrayList<>();
                for (int i = 0; i < searchResultsArray.size(); i++) {
                    JsonObject item = searchResultsArray.get(i).getAsJsonObject();
                    Map<String, String> result = new HashMap<>();
                    result.put("title", item.get("title").getAsString());
                    result.put("snippet", item.get("snippet").getAsString());

                    // Construct the full URL for the article
                    String articleTitle = item.get("title").getAsString();
                    String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8.toString());

                    // Replace '+' with '_'
                    String articleUrl = "https://" + languageCode + ".wikipedia.org/wiki/"
                            + encodedTitle.replace("+", "_");

                    result.put("url", articleUrl);
                    results.add(result);
                }

                requireActivity().runOnUiThread(() -> {
                    searchResults.clear();
                    searchResults.addAll(results);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void openArticleFragment(String title, String url) {
        // Load the ArticleFragment with the passed title and URL
        Fragment articleFragment = ArticleFragment.newInstance(title, url);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, articleFragment) // Assuming fragment_container is the container ID
                .addToBackStack(null)
                .commit();
    }

    private String getLanguagePreference() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("language_code", "en"); // Default to English if no preference is set
    }
}
