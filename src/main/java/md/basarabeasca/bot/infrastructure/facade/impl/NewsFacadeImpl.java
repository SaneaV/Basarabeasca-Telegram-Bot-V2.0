package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.News;
import md.basarabeasca.bot.infrastructure.facade.NewsFacade;
import md.basarabeasca.bot.infrastructure.factory.Factory;
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
