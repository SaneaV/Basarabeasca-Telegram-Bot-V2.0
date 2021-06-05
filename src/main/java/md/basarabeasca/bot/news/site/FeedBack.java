package md.basarabeasca.bot.news.site;

import lombok.Data;
import md.basarabeasca.bot.news.model.News;
import md.basarabeasca.bot.news.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Component
public class FeedBack implements Parser {

    @Value("${site.feedback}")
    private String siteLink;

    @Override
    public Elements getNewsName() throws IOException {
        return getDocument().getElementsByClass("entry-title entry-title-big");
    }

    @Override
    public Elements getNewsDescription() throws IOException {
        return getDocument().getElementsByClass("twp-content-wrapper").select("p");
    }

    @Override
    public Elements getNewsLink() throws IOException {
        return getDocument().getElementsByClass("twp-read-more").select("a");
    }

    @Override
    public Elements getNewsImage() throws IOException {
        return getDocument().getElementsByClass("attachment-jumla-normal-post size-jumla-normal-post wp-post-image");
    }

    @Override
    public Document getDocument() throws IOException {
        return Jsoup.connect(siteLink).get();
    }

    @Override
    public List<News> getLastNews() throws IOException {
        List<News> newsList = new ArrayList<>();

        Elements names = getNewsName();
        Elements descriptions = getNewsDescription();
        Elements links = getNewsLink();
        Elements images = getNewsImage();

        for (int i = 0; i < 10; i++) {
            News news = new News();

            news.setName(names.get(i).text());
            news.setImage(images.get(i).attr("src"));
            news.setDescription(descriptions.get(i).text());
            news.setLink(links.get(i).attr("href"));

            newsList.add(news);
        }
        Collections.reverse(newsList);
        return newsList;
    }
}
