package md.basarabeasca.bot.telegram.command.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.command.NewsSiteCommand;
import md.basarabeasca.bot.infrastructure.parser.impl.FeedBackParserImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class FeedBackCommand implements NewsSiteCommand {

  private static final String FEEDBACK = "Новости FeedBack";
  private static final String LAST_10_NEWS_FEEDBACK = "Последние 10 новостей с сайта "
      + "[FeedBack](http://feedback.md)";

  private final FeedBackParserImpl feedBackParserImpl;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendNews(update.getMessage(), feedBackParserImpl.getLastNews(), LAST_10_NEWS_FEEDBACK);
  }

  @Override
  public String getCommand() {
    return FEEDBACK;
  }
}
