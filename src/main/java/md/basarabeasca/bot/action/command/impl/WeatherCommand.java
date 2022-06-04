package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getUsefulReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.parser.impl.WeatherParserImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class WeatherCommand implements Command {

  private static final String WEATHER = "Погода на неделю";

  private final WeatherParserImpl weatherParserImpl;

  @SneakyThrows
  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendWeather(update.getMessage()));
  }

  private SendMessage sendWeather(Message message) throws IOException {
    final String weather = weatherParserImpl.getWeather();

    return getSendMessageWithReplyKeyboardMarkup(message, weather, getUsefulReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return WEATHER;
  }
}
