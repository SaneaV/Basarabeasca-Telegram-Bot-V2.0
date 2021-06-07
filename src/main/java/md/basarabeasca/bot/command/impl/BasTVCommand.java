package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.bot.BasarabeascaBot;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import md.basarabeasca.bot.news.model.News;
import md.basarabeasca.bot.news.site.BasTV;
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
public class BasTVCommand implements ICommand {

    private final BasTV basTV;
    private final BasarabeascaBot basarabeascaBot;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendBasTVNews(update.getMessage());
    }

    private SendMessage sendBasTVNews(final Message message) throws IOException {
        List<News> list = basTV.getLastNews();

        assert list != null;
        for (News news : list) {

            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(message.getChatId().toString())
                    .photo(new InputFile(news.getImage()))
                    .parseMode("markdown")
                    .caption("*" + news.getName() + "*" + "\n\n" + news.getDescription())
                    .build();

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
                    .url(news.getLink())
                    .text("Продолжить чтение")
                    .build();

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

        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Последние " + list.size() + " новостей с сайта https://bas-tv.md/")
                .replyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup())
                .build();
    }
}
