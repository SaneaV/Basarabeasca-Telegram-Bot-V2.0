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

public interface NewsSiteParser {

  Elements getNewsTitle(Document parsedSite);

  Elements getNewsDescription(Document parsedSite);

  Elements getNewsLink(Document parsedSite);

  Elements getNewsImage(Document parsedSite);

  Document getDocument() throws IOException;

  List<News> getLastNews();

  default List<Elements> getNewsFromThreads() {
    final Document parsedSite = getParsedSite();
    final ExecutorService executorService = Executors.newFixedThreadPool(4);

    final Future<Elements> newsTitle = executorService.submit(() -> getNewsTitle(parsedSite));
    final Future<Elements> newsDescription = executorService.submit(
        () -> getNewsDescription(parsedSite));
    final Future<Elements> newsLink = executorService.submit(() -> getNewsLink(parsedSite));
    final Future<Elements> newsImage = executorService.submit(() -> getNewsImage(parsedSite));

    executorService.shutdown();

    try {
      return List.of(newsTitle.get(), newsDescription.get(), newsLink.get(), newsImage.get());
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException();
    }
  }

  default Document getParsedSite() {
    try {
      return getDocument();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
