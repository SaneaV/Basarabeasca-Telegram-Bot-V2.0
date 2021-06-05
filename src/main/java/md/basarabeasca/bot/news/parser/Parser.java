package md.basarabeasca.bot.news.parser;

import md.basarabeasca.bot.news.model.News;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public interface Parser {

    Elements getNewsName() throws IOException;

    Elements getNewsDescription() throws IOException;

    Elements getNewsLink() throws IOException;

    Elements getNewsImage() throws IOException;

    Document getDocument() throws IOException;

    List<News> getLastNews() throws IOException;
}
