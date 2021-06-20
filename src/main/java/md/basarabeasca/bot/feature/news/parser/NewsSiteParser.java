package md.basarabeasca.bot.feature.news.parser;

import md.basarabeasca.bot.feature.news.model.News;
import md.basarabeasca.bot.settings.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public interface NewsSiteParser extends Parser {

    Elements getNewsName() throws IOException;

    Elements getNewsDescription() throws IOException;

    Elements getNewsLink() throws IOException;

    Elements getNewsImage() throws IOException;

    Document getDocument() throws IOException;

    List<News> getLastNews() throws IOException, InterruptedException;
}
