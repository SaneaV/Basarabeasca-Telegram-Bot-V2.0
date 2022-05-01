package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.NewsSiteCommand;
import md.basarabeasca.bot.parser.impl.FeedBackParserImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedBackCommand implements NewsSiteCommand {

    private static final String FEEDBACK = "Новости FeedBack";
    private static final String LAST_10_NEWS_FEEDBACK = "Последние 10 новостей с сайта "
        + "[FeedBack](http://feedback.md)";

    private final FeedBackParserImpl feedBackParserImpl;

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return sendFeedBackNews(update.getMessage());
    }

    private List<? super PartialBotApiMethod<?>> sendFeedBackNews(Message message) {
        return sendNews(message, feedBackParserImpl.getLastNews(), LAST_10_NEWS_FEEDBACK);
    }

    @Override
    public String getCommand() {
        return FEEDBACK;
    }
}
