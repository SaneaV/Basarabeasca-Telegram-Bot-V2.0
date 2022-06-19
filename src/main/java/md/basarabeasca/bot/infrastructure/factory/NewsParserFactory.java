package md.basarabeasca.bot.infrastructure.factory;

import md.basarabeasca.bot.infrastructure.parser.NewsParser;

public interface NewsParserFactory {

  NewsParser getNewsParser(String source);
}
