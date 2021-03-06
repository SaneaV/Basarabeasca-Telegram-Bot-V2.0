package md.basarabeasca.bot.infrastructure.parser.impl;

import static com.google.common.collect.Lists.reverse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import md.basarabeasca.bot.dao.domain.News;
import md.basarabeasca.bot.infrastructure.parser.NewsParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DistrictCouncilParserImpl implements NewsParser {

  private static final String DISTRICT_COUNCIL = "Новости Районного совета";
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
  public Elements getTitle(Document parsedSite) {
    return parsedSite.select(ITEMPROP_NAME);
  }

  @Override
  public Elements getDescription(Document parsedSite) {
    return parsedSite.select(ITEMPROP_ARTICLE_SECTION).next();
  }

  @Override
  public Elements getLink(Document parsedSite) {
    return parsedSite.getElementsByClass(THUMB).select(A);
  }

  @Override
  public Elements getImage(Document parsedSite) {
    return parsedSite.getElementsByClass(THUMB).select(A).select(IMG);
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
    return DISTRICT_COUNCIL;
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

  private List<News> populateListOfNews(Elements titles, Elements descriptions, Elements links,
      Elements images) {
    final List<News> newsList = new ArrayList<>();

    IntStream.range(0, 10)
        .forEach(number -> newsList.add(new News(
            titles.get(number).text(),
            descriptions.get(number).text(),
            images.get(number).attr(SRC),
            links.get(number).attr(HREF))
        ));
    return newsList;
  }
}
