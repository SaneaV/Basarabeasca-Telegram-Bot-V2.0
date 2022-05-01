package md.basarabeasca.bot.action.util.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static java.util.Arrays.asList;

public class ReplyKeyboardMarkupUtil {

    private static final String NEWS = "Новости города";
    private static final String USEFUL = "Полезная информация";

    private static final String SHOW_NUMBERS = "Полезные номера";
    private static final String WEATHER = "Погода на неделю";
    private static final String PUBLIC_TRANSPORT_TIMETABLE = "Расписание междугородних рейсов";
    private static final String MONEY = "Деньги";

    private static final String BASTV = "Новости BasTV";
    private static final String FEEDBACK = "Новости FeedBack";
    private static final String DISTRICT_COUNCIL = "Новости Районного совета";

    private static final String EXCHANGE_RATES = "Курс валют BNM";
    private static final String BEST_EXCHANGE = "Лучший курс обмена";

    private static final String BUY_CURRENCY = "Купить валюту";
    private static final String SELL_CURRENCY = "Продать валюту";

    private static final String USD = "%s USD";
    private static final String EUR = "%s EUR";
    private static final String RUB = "%s RUB";
    private static final String RON = "%s RON";
    private static final String UAH = "%s UAH";

    private static final String BACK_TO_MAIN_MENU = "Вернуться в главное меню";

    public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup() {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();

        keyboardRowOne.addAll(List.of(NEWS));
        keyboardRowTwo.addAll(List.of(USEFUL));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo));
    }

    public static ReplyKeyboardMarkup getUsefulReplyKeyboardMarkup() {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();
        final KeyboardRow keyboardRowThree = new KeyboardRow();
        final KeyboardRow keyboardRowFour = new KeyboardRow();
        final KeyboardRow keyboardRowFive = new KeyboardRow();

        keyboardRowOne.addAll(List.of(SHOW_NUMBERS));
        keyboardRowTwo.addAll(List.of(WEATHER));
        keyboardRowThree.addAll(List.of(PUBLIC_TRANSPORT_TIMETABLE));
        keyboardRowFour.addAll(List.of(MONEY));
        keyboardRowFive.addAll(List.of(BACK_TO_MAIN_MENU));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree, keyboardRowFour,
                keyboardRowFive));
    }

    public static ReplyKeyboardMarkup getMoneyReplyKeyboardMarkup() {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();
        final KeyboardRow keyboardRowThree = new KeyboardRow();

        keyboardRowOne.addAll(List.of(EXCHANGE_RATES));
        keyboardRowTwo.addAll(List.of(BEST_EXCHANGE));
        keyboardRowThree.addAll(List.of(BACK_TO_MAIN_MENU));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree));
    }

    public static ReplyKeyboardMarkup getCurrencyActionReplyKeyboardMarkup() {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();
        final KeyboardRow keyboardRowThree = new KeyboardRow();

        keyboardRowOne.addAll(List.of(BUY_CURRENCY));
        keyboardRowTwo.addAll(List.of(SELL_CURRENCY));
        keyboardRowThree.addAll(List.of(BACK_TO_MAIN_MENU));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree));
    }

    public static ReplyKeyboardMarkup getCurrencyReplyKeyboardMarkup(String action) {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();
        final KeyboardRow keyboardRowThree = new KeyboardRow();
        final KeyboardRow keyboardRowFour = new KeyboardRow();
        final KeyboardRow keyboardRowFive = new KeyboardRow();
        final KeyboardRow keyboardRowSix = new KeyboardRow();

        keyboardRowOne.addAll(List.of(String.format(USD, action)));
        keyboardRowTwo.addAll(List.of(String.format(EUR, action)));
        keyboardRowThree.addAll(List.of(String.format(RUB, action)));
        keyboardRowFour.addAll(List.of(String.format(RON, action)));
        keyboardRowFive.addAll(List.of(String.format(UAH, action)));
        keyboardRowSix.addAll(List.of(BACK_TO_MAIN_MENU));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree, keyboardRowFour,
                keyboardRowFive, keyboardRowSix));
    }

    public static ReplyKeyboardMarkup getNewsReplyKeyboardMarkup() {
        final KeyboardRow keyboardRowOne = new KeyboardRow();
        final KeyboardRow keyboardRowTwo = new KeyboardRow();
        final KeyboardRow keyboardRowThree = new KeyboardRow();
        final KeyboardRow keyboardRowFour = new KeyboardRow();

        keyboardRowOne.addAll(List.of(BASTV));
        keyboardRowTwo.addAll(List.of(FEEDBACK));
        keyboardRowThree.addAll(List.of(DISTRICT_COUNCIL));
        keyboardRowFour.addAll(List.of(BACK_TO_MAIN_MENU));

        return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree, keyboardRowFour));
    }

    private static ReplyKeyboardMarkup createReplyKeyBoardMarkup(List<KeyboardRow> keyboardRowList) {
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboardRowList)
                .build();
    }
}
