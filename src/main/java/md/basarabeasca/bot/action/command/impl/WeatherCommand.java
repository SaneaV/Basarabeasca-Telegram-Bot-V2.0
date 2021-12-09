package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.feature.weather.service.ParseWeather;
import md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class WeatherCommand implements Command {

    private static final String WEATHER = "Погода на неделю";

    private final ParseWeather parseWeather;

    @SneakyThrows
    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return singletonList(sendWeather(update.getMessage()));
    }

    private SendMessage sendWeather(Message message) throws IOException {
        String weather = parseWeather.getWeather();

        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                weather, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }

    @Override
    public String getCommand() {
        return WEATHER;
    }
}
