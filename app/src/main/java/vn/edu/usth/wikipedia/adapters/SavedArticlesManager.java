package vn.edu.usth.wikipedia.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// Manager class for handling saved articles
public class SavedArticlesManager {

    private static final String ARTICLES_DIR = "saved_articles"; // Directory for saved articles
    private static final String PREFS_NAME = "saved_articles_prefs"; // SharedPreferences file name
    private static final String PREFS_KEY = "saved_articles_set"; // Key for the set of saved articles

    private Set<String> savedArticles; // Set to hold saved article URLs
    private final File articlesDir; // Directory for saving article content
    private final SharedPreferences sharedPreferences; // SharedPreferences for storing saved articles

    // Constructor to initialize the manager
    public SavedArticlesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        articlesDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ARTICLES_DIR);

        // Create directory if it doesn't exist
        if (!articlesDir.exists()) {
            articlesDir.mkdirs();
        }

        loadSavedArticles(); // Load saved articles from SharedPreferences
    }

    // Load saved articles from SharedPreferences
    private void loadSavedArticles() {
        Set<String> savedArticlesSet = sharedPreferences.getStringSet(PREFS_KEY, new HashSet<>());
        if (savedArticlesSet != null) {
            savedArticles = new HashSet<>(savedArticlesSet);
        } else {
            savedArticles = new HashSet<>();
        }
    }

    // Add a new article to the list of saved articles
    public void addArticle(String url, String title) {
        savedArticles.add(url);
        saveArticlesToPreferences(); // Save updated list to SharedPreferences
        try {
            // Sample content; replace with actual article content
            String content = "Sample content of the article";
            saveArticleContent(url, content.getBytes()); // Save article content to file
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }

    // Check if an article is already saved
    public boolean isArticleSaved(String url) {
        return savedArticles.contains(url);
    }

    // Remove an article from the list of saved articles
    public void removeArticle(String url) {
        savedArticles.remove(url);
        saveArticlesToPreferences(); // Save updated list to SharedPreferences
        File file = new File(getArticleFilePath(url));
        if (file.exists()) {
            file.delete(); // Delete the file
        }
    }

    // Save the set of saved articles to SharedPreferences
    private void saveArticlesToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREFS_KEY, savedArticles);
        editor.apply();
    }

    // Get the directory where articles are saved
    public File getArticlesDir() {
        return articlesDir;
    }

    // Get the file path for a saved article based on its URL
    public String getArticleFilePath(String url) {
        return new File(articlesDir, url.hashCode() + ".mht").getAbsolutePath();
    }

    // Fetch or derive the title for an article
    public String getArticleTitle(String url) {
        return "Title for: " + url; // Placeholder; replace with actual title fetching logic
    }

    // Save article content to a file
    public void saveArticleContent(String url, byte[] content) throws IOException {
        File file = new File(getArticleFilePath(url));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content); // Write content to file
        }
    }

    // Clear all saved articles and their content
    public void clearSavedArticles() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear saved articles from SharedPreferences
        editor.apply();

        File dir = getArticlesDir();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete(); // Delete each file
            }
        }
    }
}
