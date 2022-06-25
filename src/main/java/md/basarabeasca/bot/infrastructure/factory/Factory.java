package md.basarabeasca.bot.infrastructure.factory;

import md.basarabeasca.bot.infrastructure.parser.NewsParser;

public interface Factory {

  NewsParser getNewsParser(String source);
}
