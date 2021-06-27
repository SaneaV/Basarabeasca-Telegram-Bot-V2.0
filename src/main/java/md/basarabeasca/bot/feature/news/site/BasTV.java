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

    private List<News> getListNews() {
        List<Elements> list = getNewsFromThreads();

        Elements names = list.get(0);
        Elements descriptions = list.get(1);
        Elements links = list.get(2);
        Elements images = list.get(3);

        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            News news = News.builder()
                    .name(names.get(i).text())
                    .description(descriptions.get(i).text())
                    .image(images.get(i).attr("data-src"))
                    .link(links.get(i).attr("href"))
                    .build();

            newsList.add(news);
        }

        return newsList;
    }

    @Override
    public List<News> getLastNews() {
        List<News> list = getListNews();
        Collections.reverse(list);
        return list;
    }
}
