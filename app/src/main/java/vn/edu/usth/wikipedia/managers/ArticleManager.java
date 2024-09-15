package vn.edu.usth.wikipedia.managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.wikipedia.models.Article;

/**
 * Manager for handling saved articles, including checking if articles are saved,
 * adding, and removing saved articles.
 */
public class ArticleManager {

    private static final String ARTICLES_DIR = "saved_articles"; // Directory name for saved articles
    private static final String TAG = "ArticleManager"; // Log tag for error reporting
    private File articlesDir; // Directory where articles are stored

    /**
     * Constructor for ArticleManager. Initializes the directory for saved articles.
     *
     * @param context The application context.
     */
    public ArticleManager(Context context) {
        // Set the directory for saved articles within the external files directory
        articlesDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ARTICLES_DIR);
        // Create the directory if it doesn't exist
        if (!articlesDir.exists()) {
            articlesDir.mkdirs();
        }
    }

    /**
     * Example method to get a list of popular articles.
     *
     * @return A list of popular articles.
     */
    public List<Article> getPopularArticles() {
        List<Article> popularArticles = new ArrayList<>();
        // Replace this with actual implementation to fetch articles from a network source or database

        // For demonstration purposes, adding dummy popular articles
        popularArticles.add(new Article("Popular Article 1", "http://example.com/article1"));
        popularArticles.add(new Article("Popular Article 2", "http://example.com/article2"));
        popularArticles.add(new Article("Popular Article 3", "http://example.com/article3"));

        return popularArticles;
    }

    /**
     * Adds an article to the saved articles directory.
     *
     * @param url The URL of the article.
     * @param title The title of the article.
     */
    public void addArticle(String url, String title) {
        try {
            // Create a new file for the article
            File articleFile = new File(getArticleFilePath(url));
            if (!articleFile.exists()) {
                articleFile.createNewFile();
            }
        } catch (IOException e) {
            // Log an error if there is an issue creating the file
            Log.e(TAG, "Error adding article", e);
        }
    }

    /**
     * Checks if an article is saved.
     *
     * @param url The URL of the article.
     * @return True if the article is saved, false otherwise.
     */
    public boolean isArticleSaved(String url) {
        // Check if the article file exists in the saved articles directory
        return new File(getArticleFilePath(url)).exists();
    }

    /**
     * Removes an article from the saved articles directory.
     *
     * @param url The URL of the article to remove.
     */
    public void removeArticle(String url) {
        File articleFile = new File(getArticleFilePath(url));
        if (articleFile.exists()) {
            articleFile.delete(); // Delete the file if it exists
        }
    }

    /**
     * Gets the file path for a saved article based on its URL.
     *
     * @param url The URL of the article.
     * @return The absolute file path for the article.
     */
    public String getArticleFilePath(String url) {
        // Return the file path with a hash of the URL as the file name and ".mht" extension
        return new File(articlesDir, url.hashCode() + ".mht").getAbsolutePath();
    }

    /**
     * Gets the directory where articles are saved.
     *
     * @return The directory for saved articles.
     */
    public File getArticlesDir() {
        return articlesDir;
    }
}
