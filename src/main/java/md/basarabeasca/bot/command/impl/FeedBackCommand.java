package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.bot.BasarabeascaBot;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import md.basarabeasca.bot.news.model.News;
import md.basarabeasca.bot.news.site.FeedBack;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class FeedBackCommand implements ICommand {

    private final FeedBack feedBack;
    private final BasarabeascaBot basarabeascaBot;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendFeedBackNews(update.getMessage());
    }

    private SendMessage sendFeedBackNews(final Message message) throws IOException {
        List<News> list = feedBack.getLastNews();

        assert list != null;
        for (News news : list) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(message.getChatId().toString());
            sendPhoto.setPhoto(new InputFile(news.getImage()));
            sendPhoto.setParseMode("markdown");
            sendPhoto.setCaption("*" + news.getName() + "*" + "\n\n" + news.getDescription());

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setUrl(news.getLink());
            inlineKeyboardButton.setText("Продолжить чтение");

            rowInline.add(inlineKeyboardButton);
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            sendPhoto.setReplyMarkup(markupInline);
            try {
                basarabeascaBot.execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Последние " + list.size() + " новостей с сайта http://feedback.md/");
        sendMessage.setReplyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup(message));
        return sendMessage;
    }
}
