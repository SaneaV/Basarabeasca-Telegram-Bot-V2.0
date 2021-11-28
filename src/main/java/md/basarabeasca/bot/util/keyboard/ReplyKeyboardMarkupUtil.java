package md.basarabeasca.bot.util.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkupUtil {

    private static final String BASTV = "BasTV";
    private static final String FEEDBACK = "FeedBack";
    private static final String SHOW_NUMBERS = "Полезные номера";
    private static final String WEATHER = "Погода на неделю";

    public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup() {
        KeyboardRow keyboardRowOne = new KeyboardRow();
        KeyboardRow keyboardRowTwo = new KeyboardRow();
        KeyboardRow keyboardRowThree = new KeyboardRow();

        keyboardRowOne.addAll(List.of(FEEDBACK, BASTV));
        keyboardRowTwo.addAll(List.of(WEATHER));
        keyboardRowThree.addAll(List.of(SHOW_NUMBERS));

        List<KeyboardRow> keyboardRowList = new ArrayList<>(List.of(keyboardRowOne, keyboardRowTwo, keyboardRowThree));

        return build(keyboardRowList);
    }

    private static ReplyKeyboardMarkup build(List<KeyboardRow> keyboardRowList) {
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboardRowList)
                .build();
    }
}
