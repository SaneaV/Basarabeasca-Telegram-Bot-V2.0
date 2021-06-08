package md.basarabeasca.bot.util.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static md.basarabeasca.bot.settings.Command.BASTV;
import static md.basarabeasca.bot.settings.Command.FEEDBACK;
import static md.basarabeasca.bot.settings.Command.MAIN_MENU;
import static md.basarabeasca.bot.settings.Command.SEARCH_NUMBER_BY_DESCRIPTION;
import static md.basarabeasca.bot.settings.Command.SHOW_NUMBERS;
import static md.basarabeasca.bot.settings.Command.WEATHER;

public class ReplyKeyboardMarkupUtil {

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
