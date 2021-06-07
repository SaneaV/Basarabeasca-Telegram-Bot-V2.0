package md.basarabeasca.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardUtil {

    public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup() {
        KeyboardRow keyboardRowOne = new KeyboardRow();
        KeyboardRow keyboardRowTwo = new KeyboardRow();
        KeyboardRow keyboardRowThree = new KeyboardRow();

        keyboardRowOne.addAll(List.of("FeedBack", "BasTV"));
        keyboardRowTwo.addAll(List.of("Погода на неделю"));
        keyboardRowThree.addAll(List.of("Полезные номера"));
      
        List<KeyboardRow> keyboardRowList = new ArrayList<>(List.of(keyboardRowOne, keyboardRowTwo, keyboardRowThree));

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboardRowList)
                .build();
    }
}
