package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.command.Command;
import md.basarabeasca.bot.service.WeatherService;
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
    final SendMessage weatherForecast = getSendMessageWithReplyKeyboardMarkup(
        update.getMessage(), weather, getUsefulReplyKeyboardMarkup());
    return singletonList(weatherForecast);
  }

  @Override
  public String getCommand() {
    return WEATHER;
  }
}
