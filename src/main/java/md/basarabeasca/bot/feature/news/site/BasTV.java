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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Data
@Component
public class BasTV implements NewsSiteParser {

    @Value("${site.bastv}")
    private String siteLink;

    @Override
    public Elements getNewsName() throws IOException {
        return getDocument().getElementsByClass("title");
    }

    @Override
    public Elements getNewsDescription() throws IOException {
        return getDocument().getElementsByClass("post-summary");
    }

    @Override
    public Elements getNewsLink() throws IOException {
        return getDocument().getElementsByClass("title").select("a");
    }

    @Override
    public Elements getNewsImage() throws IOException {
        return getDocument().getElementsByClass("featured clearfix").select("a");
    }

    @Override
    public Document getDocument() throws IOException {
        return Jsoup.connect(siteLink).get();
    }

    @Override
    public List<News> getLastNews() {
        List<News> newsList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<Elements> namesFuture = executorService.submit(this::getNewsName);
        Future<Elements> descriptionsFuture = executorService.submit(this::getNewsDescription);
        Future<Elements> linksFuture = executorService.submit(this::getNewsLink);
        Future<Elements> imagesFuture = executorService.submit(this::getNewsImage);

        executorService.shutdown();

        Elements names = null;
        Elements descriptions = null;
        Elements links = null;
        Elements images = null;

        try {
            names = namesFuture.get();
            descriptions = descriptionsFuture.get();
            links = linksFuture.get();
            images = imagesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            News news = News.builder()
                    .name(names.get(i).text())
                    .description(descriptions.get(i).text())
                    .image(images.get(i).attr("data-src"))
                    .link(links.get(i).attr("href"))
                    .build();

            newsList.add(news);
        }

        Collections.reverse(newsList);
        return newsList;
    }
}
