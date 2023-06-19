package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.sendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.service.api.WeatherService;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class WeatherCommand implements Command {

  private static final String WEATHER = "Погода на неделю";

  private final WeatherService weatherService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final String weather = weatherService.getWeather();
    final SendMessage weatherForecast = sendMessageWithReplyKeyboardMarkup(
        update.getMessage(), weather, getUsefulReplyKeyboardMarkup());
    return singletonList(weatherForecast);
  }

  @Override
  public String getCommand() {
    return WEATHER;
  }
}
