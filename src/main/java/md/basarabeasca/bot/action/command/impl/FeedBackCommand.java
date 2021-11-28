package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.feature.news.model.News;
import md.basarabeasca.bot.feature.news.site.FeedBack;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static md.basarabeasca.bot.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardWithUrl;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendPhoto;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
@RequiredArgsConstructor
public class FeedBackCommand implements Command {

    private static final String ASTERISK = "*";
    private static final String FEEDBACK = "FeedBack";
    private static final String TWO_NEW_LINES = "\n\n";
    private static final String CONTINUE_READING = "Читать продолжение";
    private static final String LAST_10_NEWS_FEEDBACK = "Последние 10 новостей с сайта http://feedback.md";

    private final FeedBack feedBack;

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return sendFeedBackNews(update.getMessage());
    }

    private List<? super PartialBotApiMethod<?>> sendFeedBackNews(Message message) {
        final List<News> news = feedBack.getLastNews();
        final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

        news.forEach(
                singleNews -> {
                    SendPhoto sendPhoto = getSendPhoto(message.getChatId().toString(),
                            ASTERISK + singleNews.getName() + ASTERISK + TWO_NEW_LINES +
                                    singleNews.getDescription(), singleNews.getImage(), MARKDOWN);

                    sendPhoto.setReplyMarkup(
                            getSendInlineKeyboardWithUrl(CONTINUE_READING, singleNews.getLink()));

                    messages.add(sendPhoto);
                }
        );

        messages.add(getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                LAST_10_NEWS_FEEDBACK, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup()));

        return messages;
    }

    @Override
    public String getCommand() {
        return FEEDBACK;
    }
}
