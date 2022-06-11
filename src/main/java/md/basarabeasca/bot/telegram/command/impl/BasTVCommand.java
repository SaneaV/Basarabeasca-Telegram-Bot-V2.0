package md.basarabeasca.bot.telegram.command.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.parser.impl.BasTVParserImpl;
import md.basarabeasca.bot.telegram.command.NewsSiteCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BasTVCommand implements NewsSiteCommand {

  private static final String BASTV = "Новости BasTV";
  private static final String LAST_10_NEWS_BASTV = "Последние 10 новостей с сайта "
      + "[Bas-TV](https://bas-tv.md)";

  private final BasTVParserImpl basTVParserImpl;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendNews(update.getMessage(), basTVParserImpl.getLastNews(), LAST_10_NEWS_BASTV);
  }

  @Override
  public String getCommand() {
    return BASTV;
  }
}
