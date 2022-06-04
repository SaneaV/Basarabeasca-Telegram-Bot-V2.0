package md.basarabeasca.bot.action.command.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.NewsSiteCommand;
import md.basarabeasca.bot.parser.impl.BasTVParserImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
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
    return sendBasTVNews(update.getMessage());
  }

  private List<? super PartialBotApiMethod<?>> sendBasTVNews(Message message) {
    return sendNews(message, basTVParserImpl.getLastNews(), LAST_10_NEWS_BASTV);
  }

  @Override
  public String getCommand() {
    return BASTV;
  }
}
