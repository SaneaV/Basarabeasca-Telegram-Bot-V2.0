package md.basarabeasca.bot.infrastructure.factory.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.factory.NewsParserFactory;
import md.basarabeasca.bot.infrastructure.parser.NewsParser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsParserFactoryImpl implements NewsParserFactory {

  private final List<NewsParser> parserList;

  @Override
  public NewsParser getNewsParser(String source) {
    return parserList.stream()
        .filter(p -> p.getNewsSource().equals(source))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }
}
