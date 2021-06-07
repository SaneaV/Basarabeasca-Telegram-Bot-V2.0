package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import md.basarabeasca.bot.feature.weather.service.ParseWeather;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@AllArgsConstructor
public class WeatherCommand implements ICommand {

    private final ParseWeather parseWeather;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendWeather(update.getMessage());
    }

    private SendMessage sendWeather(final Message message) throws IOException {
        String weather = parseWeather.getWeather();

        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(weather)
                .parseMode("markdown")
                .replyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup())
                .build();
    }
}
