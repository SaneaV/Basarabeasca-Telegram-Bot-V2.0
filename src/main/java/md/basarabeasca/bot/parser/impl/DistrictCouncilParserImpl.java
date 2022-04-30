package md.basarabeasca.bot.parser.impl;

import md.basarabeasca.bot.domain.News;
import md.basarabeasca.bot.parser.NewsSiteParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.reverse;

@Component
public class DistrictCouncilParserImpl implements NewsSiteParser {

    private static final String ITEMPROP_NAME = "span[itemprop=\"name\"]";
    private static final String ITEMPROP_ARTICLE_SECTION = "p[itemprop=\"articleSection\"]";
    private static final String THUMB = "thumb";
    private static final String A = "a";
    private static final String IMG = "img";
    private static final String SRC = "src";
    private static final String HREF = "href";

    private final String siteLink;

    public DistrictCouncilParserImpl(@Value("${site.news.districtCouncil}") String siteLink) {
        this.siteLink = siteLink;
    }

    @Override
    public Elements getNewsTitle() throws IOException {
        return getDocument().select(ITEMPROP_NAME);
    }

    @Override
    public Elements getNewsDescription() throws IOException {
        return getDocument().select(ITEMPROP_ARTICLE_SECTION).next();
    }

    @Override
    public Elements getNewsLink() throws IOException {
        return getDocument().getElementsByClass(THUMB).select(A);
    }

    @Override
    public Elements getNewsImage() throws IOException {
        return getDocument().getElementsByClass(THUMB).select(A).select(IMG);
    }

    @Override
    public Document getDocument() throws IOException {
        return Jsoup.connect(siteLink).get();
    }

    private List<News> getListNews() {
        final List<Elements> list = getNewsFromThreads();

        final Elements names = list.get(0);
        final Elements descriptions = list.get(1);
        final Elements links = list.get(2);
        final Elements images = list.get(3);

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
        return reverse(getListNews());
    }
}
