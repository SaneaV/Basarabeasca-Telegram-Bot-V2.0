package md.basarabeasca.bot.action.util.message;

import md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class MessageUtil {

    public final static String TO_MUCH_REQUESTS = "Слишком много запросов. Повторите попытку позже.";

    public static SendMessage getSendMessage(String chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .parseMode(MARKDOWN)
                .text(text)
                .build();
    }

    public static SendMessage getSendMessageWithReplyKeyboardMarkup(String chatId, String text,
                                                                    ReplyKeyboardMarkup keyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(MARKDOWN)
                .replyMarkup(keyboardMarkup)
                .build();
    }

    public static SendMessage getSendMessageWithInlineKeyboardMarkup(String chatId, String text,
                                                                     InlineKeyboardMarkup keyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(MARKDOWN)
                .replyMarkup(keyboardMarkup)
                .build();
    }

    public static SendPhoto getSendPhoto(String chatId, String caption,
                                         String photo, String parseMode) {
        return SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(photo))
                .parseMode(parseMode)
                .caption(caption)
                .build();
    }

    public static SendMessage getSendMessageToMuchRequests(String chatId) {
        return getSendMessageWithReplyKeyboardMarkup(chatId,
                TO_MUCH_REQUESTS, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
