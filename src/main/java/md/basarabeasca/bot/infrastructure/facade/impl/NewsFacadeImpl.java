package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.News;
import md.basarabeasca.bot.infrastructure.facade.NewsFacade;
import md.basarabeasca.bot.infrastructure.factory.NewsParserFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsFacadeImpl implements NewsFacade {

  private final NewsParserFactory newsParserFactory;

  @Override
  public List<News> getLastNews(String source) {
    return newsParserFactory.getNewsParser(source).getLastNews();
  }
}
