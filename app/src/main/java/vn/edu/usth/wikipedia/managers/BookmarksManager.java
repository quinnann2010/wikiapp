package vn.edu.usth.wikipedia.managers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Manager for handling bookmark operations using SharedPreferences.
 */
public class BookmarksManager {

    private static final String PREFS_NAME = "bookmarks_prefs"; // SharedPreferences file name
    private static final String KEY_BOOKMARKS = "bookmarks"; // Key for storing bookmarks

    private SharedPreferences sharedPreferences; // SharedPreferences instance

    /**
     * Constructor to initialize BookmarksManager with context.
     *
     * @param context The context used to access SharedPreferences.
     */
    public BookmarksManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets the set of bookmarks.
     *
     * @return A set of bookmarked article URLs.
     */
    public Set<String> getBookmarks() {
        return sharedPreferences.getStringSet(KEY_BOOKMARKS, new HashSet<>());
    }

    /**
     * Adds a bookmark for a given article URL.
     *
     * @param articleUrl The URL of the article to bookmark.
     */
    public void addBookmark(String articleUrl) {
        Set<String> bookmarks = getBookmarks(); // Get current bookmarks
        bookmarks.add(articleUrl); // Add the new bookmark
        sharedPreferences.edit().putStringSet(KEY_BOOKMARKS, bookmarks).apply(); // Save the updated bookmarks
    }

    /**
     * Removes a bookmark for a given article URL.
     *
     * @param articleUrl The URL of the article to remove from bookmarks.
     */
    public void removeBookmark(String articleUrl) {
        Set<String> bookmarks = getBookmarks(); // Get current bookmarks
        bookmarks.remove(articleUrl); // Remove the bookmark
        sharedPreferences.edit().putStringSet(KEY_BOOKMARKS, bookmarks).apply(); // Save the updated bookmarks
    }

    /**
     * Checks if an article URL is bookmarked.
     *
     * @param articleUrl The URL of the article to check.
     * @return True if the article is bookmarked, otherwise false.
     */
    public boolean isBookmarked(String articleUrl) {
        return getBookmarks().contains(articleUrl); // Check if the URL is in the bookmarks set
    }

    /**
     * Clears all bookmarks.
     */
    public void clearBookmarks() {
        sharedPreferences.edit().remove(KEY_BOOKMARKS).apply(); // Remove all bookmarks
    }
}
