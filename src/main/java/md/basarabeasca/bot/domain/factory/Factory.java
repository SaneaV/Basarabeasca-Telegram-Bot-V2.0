package md.basarabeasca.bot.domain.factory;

import md.basarabeasca.bot.infrastructure.parser.api.NewsParser;

public interface Factory {

  NewsParser getNewsParser(String source);
}
