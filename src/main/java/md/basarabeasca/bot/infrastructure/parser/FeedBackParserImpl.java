package md.basarabeasca.bot.infrastructure.parser;

import static com.google.common.collect.Lists.reverse;
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
import org.springframework.stereotype.Component;

@Component
public class FeedBackParserImpl implements NewsParser {

  private static final String FEEDBACK = "Новости Feedback";
  private static final String TITLE = "entry-title entry-title-big";
  private static final String CONTENT_WRAPPER = "twp-content-wrapper";
  private static final String P = "p";
  private static final String A = "a";
  private static final String READ_MORE = "twp-read-more";
  private static final String ATTACHEMENT = "attachment-jumla-normal-post size-jumla-normal-post wp-post-image";
  private static final String SRC = "src";
  private static final String HREF = "href";

  private final String siteLink;

  public FeedBackParserImpl(@Value("${site.news.feedback}") String siteLink) {
    this.siteLink = siteLink;
  }

  @Override
  public Elements getTitle(Document parsedSite) {
    return parsedSite.getElementsByClass(TITLE);
  }

  @Override
  public Elements getDescription(Document parsedSite) {
    return parsedSite.getElementsByClass(CONTENT_WRAPPER).select(P);
  }

  @Override
  public Elements getLink(Document parsedSite) {
    return parsedSite.getElementsByClass(READ_MORE).select(A);
  }

  @Override
  public Elements getImage(Document parsedSite) {
    return parsedSite.getElementsByClass(ATTACHEMENT);
  }

  @Override
  public Document getHtml() throws IOException {
    return Jsoup.connect(siteLink).get();
  }

  @Override
  public List<News> getLastNews() {
    return reverse(getListNews());
  }

  @Override
  public String getNewsSource() {
    return FEEDBACK;
  }

  private List<News> getListNews() {
    final List<Elements> newsFromThreads = getNewsFromThreads();

    final Elements titles = getElements(newsFromThreads, 0);
    final Elements descriptions = getElements(newsFromThreads, 1);
    final Elements links = getElements(newsFromThreads, 2);
    final Elements images = getElements(newsFromThreads, 3);

    return populateListOfNews(titles, descriptions, links, images);
  }

  private Elements getElements(List<Elements> elements, int index) {
    return elements.get(index);
  }

  private List<News> populateListOfNews(Elements titles, Elements descriptions, Elements links, Elements images) {
    final List<News> newsList = new ArrayList<>();

    int maxSizeOfElements = getMaxSizeOfElements(titles.size(), descriptions.size(), links.size(), images.size());

    IntStream.range(0, maxSizeOfElements).forEach(number -> newsList.add(new News(
        titles.size() >= maxSizeOfElements ? titles.get(number).text() : EMPTY,
        descriptions.size() >= maxSizeOfElements ? descriptions.get(number).text() : EMPTY,
        images.size() >= maxSizeOfElements ? images.get(number).attr(SRC) : EMPTY,
        links.size() >= maxSizeOfElements ? links.get(number).attr(HREF) : EMPTY)
    ));
    return newsList;
  }
}