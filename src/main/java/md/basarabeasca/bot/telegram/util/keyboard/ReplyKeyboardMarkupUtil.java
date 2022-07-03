package md.basarabeasca.bot.telegram.util.keyboard;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class ReplyKeyboardMarkupUtil {

  private static final String NEWS = "Новости города";
  private static final String USEFUL = "Полезная информация";

  private static final String SHOW_NUMBERS = "Полезные номера";
  private static final String WEATHER = "Погода на неделю";
  private static final String PUBLIC_TRANSPORT_TIMETABLE = "Расписание междугородних рейсов";
  private static final String MONEY = "Деньги";
  private static final String FUEL = "Топливо";

  private static final String BASTV = "Новости BasTV";
  private static final String FEEDBACK = "Новости Feedback";
  private static final String DISTRICT_COUNCIL = "Новости Районного совета";

  private static final String BNM_EXCHANGE_RATES = "Курс валют BNM";
  private static final String BEST_EXCHANGE = "Лучший курс обмена";
  private static final String ALL_EXCHANGES = "Все курсы валют";
  private static final String BANK_HOURS = "График работы банков";

  private static final String ANRE_FUEL_PRICE = "Цены на топливо НАРЭ";
  private static final String BEST_FUEL_PRICE = "Лучшая цена на топливо";
  private static final String PRICES_AT_ALL_STATIONS = "Цены на всех автозаправках";

  private static final String BUY_CURRENCY = "Купить валюту";
  private static final String SELL_CURRENCY = "Продать валюту";

  private static final String USD = "%s USD";
  private static final String EUR = "%s EUR";
  private static final String RUB = "%s RUB";
  private static final String RON = "%s RON";
  private static final String UAH = "%s UAH";

  private static final String PETROL = "Бензин 95";
  private static final String DIESEL = "Дизель";
  private static final String GAS = "Газ";

  private static final String BACK_TO_MAIN_MENU = "Вернуться в главное меню";

  public static ReplyKeyboardMarkup getMainReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(NEWS));
    keyboardRowTwo.addAll(singletonList(USEFUL));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo));
  }

  public static ReplyKeyboardMarkup getUsefulReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();
    final KeyboardRow keyboardRowFive = new KeyboardRow();
    final KeyboardRow keyboardRowSix = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(MONEY));
    keyboardRowTwo.addAll(singletonList(FUEL));
    keyboardRowThree.addAll(singletonList(WEATHER));
    keyboardRowFour.addAll(singletonList(SHOW_NUMBERS));
    keyboardRowFive.addAll(singletonList(PUBLIC_TRANSPORT_TIMETABLE));
    keyboardRowSix.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree,
        keyboardRowFour, keyboardRowFive, keyboardRowSix));
  }

  public static ReplyKeyboardMarkup getMoneyReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();
    final KeyboardRow keyboardRowFive = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(BNM_EXCHANGE_RATES));
    keyboardRowTwo.addAll(singletonList(ALL_EXCHANGES));
    keyboardRowThree.addAll(singletonList(BEST_EXCHANGE));
    keyboardRowFour.addAll(singletonList(BANK_HOURS));
    keyboardRowFive.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree,
        keyboardRowFour, keyboardRowFive));
  }

  public static ReplyKeyboardMarkup getCurrencyActionReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(BUY_CURRENCY));
    keyboardRowTwo.addAll(singletonList(SELL_CURRENCY));
    keyboardRowThree.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree));
  }

  public static ReplyKeyboardMarkup getCurrencyReplyKeyboardMarkup(String action) {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();
    final KeyboardRow keyboardRowFive = new KeyboardRow();
    final KeyboardRow keyboardRowSix = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(String.format(USD, action)));
    keyboardRowTwo.addAll(singletonList(String.format(EUR, action)));
    keyboardRowThree.addAll(singletonList(String.format(RUB, action)));
    keyboardRowFour.addAll(singletonList(String.format(RON, action)));
    keyboardRowFive.addAll(singletonList(String.format(UAH, action)));
    keyboardRowSix.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(
        asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree, keyboardRowFour,
            keyboardRowFive, keyboardRowSix));
  }

  public static ReplyKeyboardMarkup getNewsReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(BASTV));
    keyboardRowTwo.addAll(singletonList(FEEDBACK));
    keyboardRowThree.addAll(singletonList(DISTRICT_COUNCIL));
    keyboardRowFour.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(
        asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree, keyboardRowFour));
  }

  public static ReplyKeyboardMarkup getFuelReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(ANRE_FUEL_PRICE));
    keyboardRowTwo.addAll(singletonList(BEST_FUEL_PRICE));
    keyboardRowThree.addAll(singletonList(PRICES_AT_ALL_STATIONS));
    keyboardRowFour.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree,
        keyboardRowFour));
  }

  public static ReplyKeyboardMarkup getBestFuelPriceReplyKeyboardMarkup() {
    final KeyboardRow keyboardRowOne = new KeyboardRow();
    final KeyboardRow keyboardRowTwo = new KeyboardRow();
    final KeyboardRow keyboardRowThree = new KeyboardRow();
    final KeyboardRow keyboardRowFour = new KeyboardRow();

    keyboardRowOne.addAll(singletonList(PETROL));
    keyboardRowTwo.addAll(singletonList(DIESEL));
    keyboardRowThree.addAll(singletonList(GAS));
    keyboardRowFour.addAll(singletonList(BACK_TO_MAIN_MENU));

    return createReplyKeyBoardMarkup(asList(keyboardRowOne, keyboardRowTwo, keyboardRowThree,
        keyboardRowFour));
  }

  private static ReplyKeyboardMarkup createReplyKeyBoardMarkup(List<KeyboardRow> keyboardRowList) {
    return ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .keyboard(keyboardRowList)
        .build();
  }
}
