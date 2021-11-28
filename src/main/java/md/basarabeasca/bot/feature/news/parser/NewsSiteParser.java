package md.basarabeasca.bot.feature.news.parser;

import md.basarabeasca.bot.feature.news.model.News;
import md.basarabeasca.bot.feature.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public interface NewsSiteParser extends Parser {

    Elements getNewsName() throws IOException;

    Elements getNewsDescription() throws IOException;

    Elements getNewsLink() throws IOException;

    Elements getNewsImage() throws IOException;

    Document getDocument() throws IOException;

    List<News> getLastNews() throws IOException, InterruptedException;

    default List<Elements> getNewsFromThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<Elements> namesFuture = executorService.submit(this::getNewsName);
        Future<Elements> descriptionsFuture = executorService.submit(this::getNewsDescription);
        Future<Elements> linksFuture = executorService.submit(this::getNewsLink);
        Future<Elements> imagesFuture = executorService.submit(this::getNewsImage);

        executorService.shutdown();

        List<Elements> list = null;
        try {
            list = List.of(namesFuture.get(),
                    descriptionsFuture.get(),
                    linksFuture.get(),
                    imagesFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return list;
    }
}
