package vn.edu.usth.wikipedia.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SavedArticlesManager {

    private static final String ARTICLES_DIR = "saved_articles";
    private static final String PREFS_NAME = "saved_articles_prefs";
    private static final String PREFS_KEY = "saved_articles_set";

    private Set<String> savedArticles;
    private final File articlesDir;
    private final SharedPreferences sharedPreferences;

    public SavedArticlesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        articlesDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ARTICLES_DIR);

        if (!articlesDir.exists()) {
            articlesDir.mkdirs();
        }

        loadSavedArticles();
    }

    private void loadSavedArticles() {
        Set<String> savedArticlesSet = sharedPreferences.getStringSet(PREFS_KEY, new HashSet<>());
        if (savedArticlesSet != null) {
            savedArticles = new HashSet<>(savedArticlesSet);
        } else {
            savedArticles = new HashSet<>();
        }
    }

    public void addArticle(String url, String title) {
        savedArticles.add(url);
        saveArticlesToPreferences();
        try {
            String content = "Sample content of the article";
            saveArticleContent(url, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isArticleSaved(String url) {
        return savedArticles.contains(url);
    }

    public void removeArticle(String url) {
        savedArticles.remove(url);
        saveArticlesToPreferences();
        File file = new File(getArticleFilePath(url));
        if (file.exists()) {
            file.delete();
        }
    }

    private void saveArticlesToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREFS_KEY, savedArticles);
        editor.apply();
    }

    public File getArticlesDir() {
        return articlesDir;
    }

    public String getArticleFilePath(String url) {
        return new File(articlesDir, url.hashCode() + ".mht").getAbsolutePath();
    }

    public String getArticleTitle(String url) {
        return "Title for: " + url;
    }

    public void saveArticleContent(String url, byte[] content) throws IOException {
        File file = new File(getArticleFilePath(url));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }
    }

    public void clearSavedArticles() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        File dir = getArticlesDir();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
