package md.basarabeasca.bot.telegram.command.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.parser.impl.DistrictCouncilParserImpl;
import md.basarabeasca.bot.telegram.command.NewsSiteCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class DistrictCouncilCommand implements NewsSiteCommand {

  private static final String DISTRICT_COUNCIL = "Новости Районного совета";
  private static final String LAST_10_NEWS_DISTRICT_COUNCIL = "Последние 10 новостей с сайта "
      + "[Районный Совет Басарабяска](https://basarabeasca.md/ru)";

  private final DistrictCouncilParserImpl districtCouncilParserImpl;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendNews(update.getMessage(), districtCouncilParserImpl.getLastNews(),
        LAST_10_NEWS_DISTRICT_COUNCIL);
  }

  @Override
  public String getCommand() {
    return DISTRICT_COUNCIL;
  }
}
