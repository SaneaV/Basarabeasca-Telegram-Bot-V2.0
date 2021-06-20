package md.basarabeasca.bot.action.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.action.command.ICommand;
import md.basarabeasca.bot.bot.BasarabeascaBot;
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

import static md.basarabeasca.bot.settings.StringUtil.CONTINUE_READING;
import static md.basarabeasca.bot.settings.StringUtil.LAST_10_NEWS_BASTV;
import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardWithUrl;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendPhoto;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
@AllArgsConstructor
@Lazy
public class BasTVCommand implements ICommand {

    private final BasTV basTV;
    private final BasarabeascaBot basarabeascaBot;

    @Override
    public SendMessage execute(Update update) throws IOException, InterruptedException {
        return sendBasTVNews(update.getMessage());
    }

    private SendMessage sendBasTVNews(final Message message) throws IOException, InterruptedException {
        List<News> list = basTV.getLastNews();

        assert list != null;
        for (News news : list) {

            SendPhoto sendPhoto = getSendPhoto(message.getChatId().toString(),
                    "*" + news.getName() + "*" + "\n\n" + news.getDescription(),
                    news.getImage(), MARKDOWN);

            sendPhoto.setReplyMarkup(
                    getSendInlineKeyboardWithUrl(CONTINUE_READING, news.getLink()));

            try {
                basarabeascaBot.execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                LAST_10_NEWS_BASTV, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
