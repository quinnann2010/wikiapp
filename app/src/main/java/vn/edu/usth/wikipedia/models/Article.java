package vn.edu.usth.wikipedia.models;

public class Article {
    // URL of the article
    private final String url;
    // Title of the article
    private final String title;
    private final String description;
    private final int imageResId;
    private final String imageUrl;

    public Article(String url, String title, String description, int imageResId, String imageUrl) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

}