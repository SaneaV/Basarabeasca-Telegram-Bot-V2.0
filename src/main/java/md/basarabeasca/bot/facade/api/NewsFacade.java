package md.basarabeasca.bot.facade.api;

import java.util.List;
import md.basarabeasca.bot.domain.news.News;

public interface NewsFacade {

  List<News> getLastNews(String source);
}
