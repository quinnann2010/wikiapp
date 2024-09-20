package vn.edu.usth.wikipedia.managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.wikipedia.R;
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
        popularArticles.add(new Article("https://vi.wikipedia.org/wiki/California_King_Bed", "California_King_Bed", "California King Bed  là một bài hát của nữ ca sĩ người Barbados Rihanna nằm trong album phòng thu thứ năm của cô, Loud (2010). Bài hát do Andrew Harr, Jermaine Jackson, Priscilla Renea, Alex Delicata sáng tác và được sản xuất bởi bộ đôi Harr & Jackson dưới cái tên chung, The Runners.  California King Bed  là ca khúc thuộc thể loại power ballad, rock và R&B. Bài hát sử dụng âm thanh đến từ guitar điện và phím piano dịu nhẹ, với lời bài hát kể về một cặp đôi tuy vẫn luôn gần gũi khi yêu nhau nhưng lại xa cách về mặt cảm xúc. Hãng đĩa Def Jam Recordings phát hành  California King Bed  làm đĩa đơn cho Loud vào ngày 13 tháng 5 năm 2011, sau khi ca khúc được Rihanna lựa chọn từ một cuộc khảo sát với người hâm mộ trên Twitter.", R.drawable.article_image_1));

        popularArticles.add(new Article("https://vi.wikipedia.org/wiki/Jake_Gyllenhaal", "Jake Gyllenhaal", "Jacob Benjamin Gyllenhaal (/ˈdʒɪlənhɔːl/;[1][2] tiếng Thụy Điển: [ˈjʏ̂lːɛnˌhɑːl];[3] sinh ngày 19 tháng 12 năm 1980) là một nam diễn viên người Mỹ đã có sự nghiệp trải dài hơn ba mươi năm với nhiều đóng góp trên cả lĩnh vực điện ảnh và sân khấu. Sinh ra trong gia đình Gyllenhaal, anh là con trai của đạo diễn Stephen Gyllenhaal và nhà biên kịch Naomi Foner, cũng như là em trai của nữ diễn viên Maggie Gyllenhaal.[4] Gyllenhaal bắt đầu diễn xuất từ khi còn nhỏ trong City Slickers (1991), tiếp theo là các vai diễn trong A Dangerous Woman (1993) và Homegrown (1998) đều do cha mình đạo diễn. Sự nghiệp của anh bứt phá với vai diễn Homer Hickam trong October Sky (1999) và màn hóa thân thành một cậu bé gặp vấn đề về thần kinh trong Donnie Darko (2001).", R.drawable.article_image_2));

        popularArticles.add(new Article("https://vi.wikipedia.org/wiki/Cristiano_Ronaldo", "Cristiano Ronaldo", "Cristiano Ronaldo dos Santos Aveiro GOIH ComM (phát âm tiếng Bồ Đào Nha: [kɾiʃˈtjɐnu ʁɔˈnaldu]; sinh ngày 5 tháng 2 năm 1985) là một cầu thủ bóng đá chuyên nghiệp người Bồ Đào Nha hiện đang thi đấu ở vị trí tiền đạo và là đội trưởng của cả câu lạc bộ Saudi Pro League Al Nassr và đội tuyển bóng đá quốc gia Bồ Đào Nha. Được đánh giá là một trong những cầu thủ xuất sắc nhất thế giới trong thế hệ của mình và là một trong những cầu thủ vĩ đại nhất mọi thời đại trong lịch sử bóng đá", R.drawable.article_image_3));

        popularArticles.add(new Article("https://en.wikipedia.org/wiki/FC_Bayern_Munich",
                "Bayern Munich Club",
                "Câu lạc bộ bóng đá Bayern München e. V. ( FCB , phát âm tiếng Đức: [ˈfuːsbalˌklʊp ˈbaɪɐn ˈmʏnçn̩] ), thường được gọi là Bayern Munich hoặc FC Bayern ( phát âm là [ˌɛft͡seː ˈbaɪɐn] ⓘ ), là mộtcâu lạc bộ thể thaocó trụ sở tạiMunich,Bavaria. Họ nổi tiếng nhất vớibóng đá, chơi ởBundesliga, giải đấu cao nhất củahệ thống giải bóng đá Đức. Bayern là câu lạc bộ thành công nhất trong lịch sử bóng đá Đức, đã giành được kỷ lục 33danh hiệu quốc gia, bao gồm mười một danh hiệu liên tiếp từ năm 2013 đến năm 2023, và kỷ lục 20cúp quốc gia, cùng với nhiều danh hiệu châu Âu.",
                R.drawable.article_image_4));

        popularArticles.add(new Article("https://en.wikipedia.org/wiki/League_of_Legends",
                "League of Legends",
                "Liên Minh Huyền Thoại ( LoL ), thường được gọi là League , là một trò chơi điện tử đấu trường trực tuyến nhiều người chơi năm 2009 do Riot Games phát triển và phát hành . Lấy cảm hứng từ Defense of the Ancients , một bản đồ tùy chỉnh cho Warcraft III , những người sáng lập Riot đã tìm cách phát triển một trò chơi độc lập cùng thể loại. Kể từ khi phát hành vào tháng 10 năm 2009, League đã trở thành trò chơi miễn phí và được kiếm tiền thông qua việc tùy chỉnh nhân vật có thể mua . Trò chơi có sẵn cho Microsoft Windows và macOS .",
                R.drawable.article_image_5));

        return popularArticles;
    }

    /**
     * Adds an article to the saved articles directory.
     *
     * @param url   The URL of the article.
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
