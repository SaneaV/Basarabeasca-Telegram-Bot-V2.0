package md.basarabeasca.bot.action.command;

import md.basarabeasca.bot.feature.news.model.News;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

import static md.basarabeasca.bot.action.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardWithUrl;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getNewsReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendPhoto;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public interface NewsSiteCommand extends Command {

    String ASTERISK = "*";
    String TWO_NEW_LINES = "\n\n";
    String CONTINUE_READING = "Читать продолжение";

    default List<? super PartialBotApiMethod<?>> sendNews(final Message message, final List<News> news,
                                                          final String lastTenNews) {
        final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

        news.forEach(
                singleNews -> {
                    final SendPhoto sendPhoto = getSendPhoto(message.getChatId().toString(),
                            ASTERISK + singleNews.getName() + ASTERISK + TWO_NEW_LINES +
                                    singleNews.getDescription(), singleNews.getImage(), MARKDOWN);

                    sendPhoto.setReplyMarkup(
                            getSendInlineKeyboardWithUrl(CONTINUE_READING, singleNews.getLink()));

                    messages.add(sendPhoto);
                }
        );

        messages.add(getSendMessageWithReplyKeyboardMarkup(message,lastTenNews, getNewsReplyKeyboardMarkup()));

        return messages;
    }

}
