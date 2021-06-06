package md.basarabeasca.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardUtil {

    public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup(final Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("FeedBack");
        row.add("BasTV");
        keyboard.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
