package md.basarabeasca.bot.infrastructure.factory.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.factory.Factory;
import md.basarabeasca.bot.infrastructure.parser.NewsParser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactoryImpl implements Factory {

  private final List<NewsParser> parserList;

  @Override
  public NewsParser getNewsParser(String source) {
    return parserList.stream()
        .filter(p -> p.getNewsSource().equals(source))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }
}
