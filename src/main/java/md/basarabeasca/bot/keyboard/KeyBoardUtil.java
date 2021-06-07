package md.basarabeasca.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardUtil {

    public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup() {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        keyboardRow.addAll(List.of("FeedBack", "BasTV"));
        keyboardRowList.add(keyboardRow);

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboardRowList)
                .build();
    }
}
