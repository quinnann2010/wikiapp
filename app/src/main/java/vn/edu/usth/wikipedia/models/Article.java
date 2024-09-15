package vn.edu.usth.wikipedia.models;

/**
 * Represents an Article with a URL and a Title.
 */
public class Article {
    // URL of the article
    private final String url;
    // Title of the article
    private final String title;

    /**
     * Constructor to initialize the Article object with URL and Title.
     *
     * @param url   The URL of the article.
     * @param title The title of the article.
     */
    public Article(String url, String title) {
        this.url = url;
        this.title = title;
    }

    /**
     * Gets the URL of the article.
     *
     * @return The URL of the article.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the title of the article.
     *
     * @return The title of the article.
     */
    public String getTitle() {
        return title;
    }
}
