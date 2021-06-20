package md.basarabeasca.bot.feature.news.site;

import lombok.Data;
import md.basarabeasca.bot.feature.news.model.News;
import md.basarabeasca.bot.feature.news.parser.NewsSiteParser;
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
public class FeedBack implements NewsSiteParser{

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
    public List<News> getLastNews() throws InterruptedException {
        List<News> newsList = new ArrayList<>();

        final Elements[] names = new Elements[1];
        final Elements[] descriptions = new Elements[1];
        final Elements[] links = new Elements[1];
        final Elements[] images = new Elements[1];

        Thread threadName = new Thread(() -> {
            try {
                names[0] = getNewsName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread threadDescription = new Thread(() -> {
            try {
                descriptions[0] = getNewsDescription();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread threadLinks = new Thread(() -> {
            try {
                links[0] = getNewsLink();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread threadImages = new Thread(() -> {
            try {
                images[0] = getNewsImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        threadName.start();
        threadDescription.start();
        threadLinks.start();
        threadImages.start();

        threadName.join();
        threadDescription.join();
        threadLinks.join();
        threadImages.join();

        for (int i = 0; i < 10; i++) {
            News news = News.builder()
                    .name(names[0].get(i).text())
                    .description(descriptions[0].get(i).text())
                    .image(images[0].get(i).attr("src"))
                    .link(links[0].get(i).attr("href"))
                    .build();

            newsList.add(news);
        }
        Collections.reverse(newsList);
        return newsList;
    }
}
