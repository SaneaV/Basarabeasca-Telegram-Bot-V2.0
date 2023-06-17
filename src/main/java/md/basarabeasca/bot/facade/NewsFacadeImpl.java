package md.basarabeasca.bot.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.news.News;
import md.basarabeasca.bot.facade.api.NewsFacade;
import md.basarabeasca.bot.domain.factory.Factory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsFacadeImpl implements NewsFacade {

  private final Factory factory;

  @Override
  public List<News> getLastNews(String source) {
    return factory.getNewsParser(source).getLastNews();
  }
}
