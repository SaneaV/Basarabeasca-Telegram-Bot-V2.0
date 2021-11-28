package md.basarabeasca.bot.feature.news.site;

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

@Component
public class FeedBack implements NewsSiteParser {

    private static final String TITLE = "entry-title entry-title-big";
    private static final String CONTENT_WRAPPER = "twp-content-wrapper";
    private static final String P = "p";
    private static final String A = "a";
    private static final String READ_MORE = "twp-read-more";
    private static final String ATTACHEMENT = "attachment-jumla-normal-post size-jumla-normal-post wp-post-image";
    private static final String SRC = "src";
    private static final String HREF = "href";

    private final String siteLink;

    public FeedBack(@Value("${site.feedback}") String siteLink) {
        this.siteLink = siteLink;
    }

    @Override
    public Elements getNewsName() throws IOException {
        return getDocument().getElementsByClass(TITLE);
    }

    @Override
    public Elements getNewsDescription() throws IOException {
        return getDocument().getElementsByClass(CONTENT_WRAPPER).select(P);
    }

    @Override
    public Elements getNewsLink() throws IOException {
        return getDocument().getElementsByClass(READ_MORE).select(A);
    }

    @Override
    public Elements getNewsImage() throws IOException {
        return getDocument().getElementsByClass(ATTACHEMENT);
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
                    .image(images.get(i).attr(SRC))
                    .link(links.get(i).attr(HREF))
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
