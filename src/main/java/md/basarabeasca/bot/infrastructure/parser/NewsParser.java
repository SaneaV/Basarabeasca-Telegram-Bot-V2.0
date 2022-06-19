package md.basarabeasca.bot.infrastructure.parser;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import md.basarabeasca.bot.dao.domain.News;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface NewsParser {

  Elements getTitle(Document parsedSite);

  Elements getDescription(Document parsedSite);

  Elements getLink(Document parsedSite);

  Elements getImage(Document parsedSite);

  Document getHtml() throws IOException;

  List<News> getLastNews();

  String getNewsSource();

  default List<Elements> getNewsFromThreads() {
    final Document parsedSite = getParsedSite();
    final ExecutorService executorService = Executors.newFixedThreadPool(4);

    final Future<Elements> newsTitle = executorService.submit(() -> getTitle(parsedSite));
    final Future<Elements> newsDescription = executorService.submit(
        () -> getDescription(parsedSite));
    final Future<Elements> newsLink = executorService.submit(() -> getLink(parsedSite));
    final Future<Elements> newsImage = executorService.submit(() -> getImage(parsedSite));

    executorService.shutdown();

    try {
      return List.of(newsTitle.get(), newsDescription.get(), newsLink.get(), newsImage.get());
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException();
    }
  }

  default Document getParsedSite() {
    try {
      return getHtml();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}