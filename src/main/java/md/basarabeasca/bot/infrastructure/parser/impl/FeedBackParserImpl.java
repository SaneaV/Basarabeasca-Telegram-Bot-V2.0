package md.basarabeasca.bot.infrastructure.parser.impl;

import static com.google.common.collect.Lists.reverse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import md.basarabeasca.bot.dao.domain.News;
import md.basarabeasca.bot.infrastructure.parser.NewsSiteParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeedBackParserImpl implements NewsSiteParser {

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
  public Elements getNewsTitle(Document parsedSite) {
    return parsedSite.getElementsByClass(TITLE);
  }

  @Override
  public Elements getNewsDescription(Document parsedSite) {
    return parsedSite.getElementsByClass(CONTENT_WRAPPER).select(P);
  }

  @Override
  public Elements getNewsLink(Document parsedSite) {
    return parsedSite.getElementsByClass(READ_MORE).select(A);
  }

  @Override
  public Elements getNewsImage(Document parsedSite) {
    return parsedSite.getElementsByClass(ATTACHEMENT);
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

    final List<News> newsList = new ArrayList<>();

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
