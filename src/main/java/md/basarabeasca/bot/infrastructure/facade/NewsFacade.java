package md.basarabeasca.bot.infrastructure.facade;

import java.util.List;
import md.basarabeasca.bot.dao.domain.News;

public interface NewsFacade {

  List<News> getLastNews(String source);
}
