package md.basarabeasca.bot.infrastructure.parser;

import static java.util.Collections.reverse;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.BAS_TV;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import md.basarabeasca.bot.domain.news.News;
import md.basarabeasca.bot.infrastructure.parser.api.NewsParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BasTVParserImpl implements NewsParser {

  private static final String BASTV = "Новости BasTV";
  private static final String TITLE_CLASS = "elementor-post__title";
  private static final String IMAGE_CLASS = "elementor-post__thumbnail";
  private static final String DATA_SRC_ATTR = "data-src";
  private static final String HREF_ATTR = "href";
  private static final String A_ATTR = "a";
  private static final String IMG_ATTR = "img";

  private final String siteLink;

  public BasTVParserImpl(@Value("${site.news.bastv}") String siteLink) {
    this.siteLink = siteLink;
  }

  @Override
  public Elements getTitle(Document parsedSite) {
    return parsedSite.getElementsByClass(TITLE_CLASS);
  }

  @Override
  public Elements getDescription(Document parsedSite) {
    return new Elements();
  }

  @Override
  public Elements getLink(Document parsedSite) {
    return getTitle(parsedSite).select(A_ATTR);
  }

  @Override
  public Elements getImage(Document parsedSite) {
    return parsedSite.getElementsByClass(IMAGE_CLASS).select(IMG_ATTR);
  }

  @Override
  public Document getHtml() throws IOException {
    return Jsoup.connect(siteLink).get();
  }

  @Override
  @Cacheable(value = BAS_TV, cacheManager = "jCacheCacheManager")
  public List<News> getLastNews() {
    final List<News> listNews = getListNews();
    reverse((listNews));
    return listNews;
  }

  @Override
  public String getNewsSource() {
    return BASTV;
  }

  private List<News> getListNews() {
    final List<Elements> newsFromThreads = getNewsFromThreads();

    final Elements titles = getElements(newsFromThreads, 0);
    final Elements links = getElements(newsFromThreads, 2);
    final Elements images = getElements(newsFromThreads, 3);

    return populateListOfNews(titles, links, images);
  }

  private Elements getElements(List<Elements> elements, int index) {
    return elements.get(index);
  }

  private List<News> populateListOfNews(Elements titles, Elements links, Elements images) {
    final List<News> newsList = new ArrayList<>();

    int maxSizeOfElements = getMaxSizeOfElements(titles.size(), 0, links.size(),
        images.size());

    IntStream.range(0, maxSizeOfElements).forEach(number -> newsList.add(new News(
        titles.size() >= maxSizeOfElements ? titles.get(number).text() : EMPTY,
        EMPTY,
        images.size() >= maxSizeOfElements ? images.get(number).attr(DATA_SRC_ATTR) : EMPTY,
        links.size() >= maxSizeOfElements ? links.get(number).attr(HREF_ATTR) : EMPTY)));
    return newsList;
  }
}