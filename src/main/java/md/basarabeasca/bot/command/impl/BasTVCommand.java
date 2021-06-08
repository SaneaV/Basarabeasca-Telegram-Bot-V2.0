package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.bot.BasarabeascaBot;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.feature.news.model.News;
import md.basarabeasca.bot.feature.news.site.BasTV;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboard;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendPhoto;

@Component
@AllArgsConstructor
public class BasTVCommand implements ICommand {

    private final BasTV basTV;
    @Lazy
    private final BasarabeascaBot basarabeascaBot;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendBasTVNews(update.getMessage());
    }

    private SendMessage sendBasTVNews(final Message message) throws IOException {
        List<News> list = basTV.getLastNews();

        assert list != null;
        for (News news : list) {

            SendPhoto sendPhoto = getSendPhoto(message.getChatId().toString(),
                    "*" + news.getName() + "*" + "\n\n" + news.getDescription(),
                    news.getImage(), "markdown");

            sendPhoto.setReplyMarkup(
                    getSendInlineKeyboard("Продожить чтение", news.getLink()));

            try {
                basarabeascaBot.execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                "Последние " + list.size() + " новостей с сайта https://bas-tv.md/",
                ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
