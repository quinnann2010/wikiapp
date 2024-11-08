package vn.edu.usth.wikipedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;
import vn.edu.usth.wikipedia.models.Article;
import vn.edu.usth.wikipedia.adapters.ArticleAdapter;
import vn.edu.usth.wikipedia.R;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false); // Inflate the layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        articles = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set up layout manager
        articleAdapter = new ArticleAdapter(articles, article -> openArticle(article)); // Handle article click
        recyclerView.setAdapter(articleAdapter); // Set up adapter

        loadExploreContent(); // Load articles

        Button showButton = view.findViewById(R.id.show_vu);
        showButton.setOnClickListener(v -> recyclerView.setVisibility(View.VISIBLE)); // Show RecyclerView on button click
    }

    /*  LOAD TFA (TOP FEATURE ARTICLE) BY DAY*/

    // Clear existing articles before loading new ones
    private void loadExploreContent() {

        // Repeat for 5 days to get featured articles
        for (int i = 0; i < 8; i++) {
            String date = getDateForDaysAgo(i); // Get date for 'daysAgo'
            String url = "https://api.wikimedia.org/feed/v1/wikipedia/en/featured/" + date; // Build URL

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() { // Asynchronous call
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Failed to load explore content", Toast.LENGTH_SHORT).show()); // Show error message
                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response); // Check response

                    String responseData = response.body().string(); // Get response body as string
                    Gson gson = new Gson(); // Create Gson instance
                    JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class); // Parse JSON

                    // Check if "tfa" field exists
                    if (jsonObject.has("tfa")) {
                        JsonObject tfa = jsonObject.getAsJsonObject("tfa");

                        // Add article from "tfa" to the list
                        String title = tfa.getAsJsonObject("titles").get("display").getAsString(); // Get title
                        String extract = tfa.get("extract").getAsString(); // Get description
                        String articleUrl = tfa.getAsJsonObject("content_urls").getAsJsonObject("desktop").get("page").getAsString(); // Get article URL

                        // Get image URL if available
                        String imageUrl = "";
                        if (tfa.has("thumbnail")) {
                            JsonObject thumbnail = tfa.getAsJsonObject("thumbnail");
                            imageUrl = thumbnail.get("source").getAsString(); // Get image URL
                        }

                        articles.add(new Article(articleUrl, title, extract, 0, imageUrl)); // Add article to the list
                    }

                    requireActivity().runOnUiThread(() -> {
                        articleAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    });
                }
            });
        }

    }

    private String getDateForDaysAgo(int daysAgo) {
        // Get the date for 'daysAgo' days ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }


    private void openArticle(Article article) {
        recyclerView.setVisibility(View.GONE); // Hide recycler view
        Fragment articleFragment = ArticleFragment.newInstance(article.getTitle(), article.getUrl()); // Create new fragment instance
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, articleFragment) // Replace current fragment
                .addToBackStack(null) // Add to back stack
                .commit();
    }

    private String getLanguagePreference() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("language_code", "en"); // Return user's language preference
    }
}