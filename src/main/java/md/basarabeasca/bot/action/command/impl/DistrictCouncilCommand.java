package md.basarabeasca.bot.action.command.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.NewsSiteCommand;
import md.basarabeasca.bot.feature.news.site.DistrictCouncil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DistrictCouncilCommand implements NewsSiteCommand {

    private static final String DISTRICT_COUNCIL = "Новости Районного совета";
    private static final String LAST_10_NEWS_DISTRICT_COUNCIL = "Последние 10 новостей с сайта https://basarabeasca.md/ru";

    private final DistrictCouncil districtCouncil;

    @Override
    public List<? super PartialBotApiMethod<?>> execute(Update update) {
        return sendBasTVNews(update.getMessage());
    }

    private List<? super PartialBotApiMethod<?>> sendBasTVNews(Message message) {
        return sendNews(message, districtCouncil.getLastNews(), LAST_10_NEWS_DISTRICT_COUNCIL);
    }

    @Override
    public String getCommand() {
        return DISTRICT_COUNCIL;
    }
}