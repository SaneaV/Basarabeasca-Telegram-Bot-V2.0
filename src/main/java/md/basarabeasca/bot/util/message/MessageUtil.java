package md.basarabeasca.bot.util.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageUtil {

    public static SendMessage getSendMessage(final String chatId, final String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .parseMode("markdown")
                .text(text)
                .build();
    }

    public static SendMessage getSendMessageWithReplyKeyboardMarkup(final String chatId, final String text,
                                                                    final ReplyKeyboardMarkup keyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("markdown")
                .replyMarkup(keyboardMarkup)
                .build();
    }

    public static SendPhoto getSendPhoto(final String chatId, final String caption,
                                         final String photo, final String parseMode) {
        return SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(photo))
                .parseMode(parseMode)
                .caption(caption)
                .build();
    }
}
