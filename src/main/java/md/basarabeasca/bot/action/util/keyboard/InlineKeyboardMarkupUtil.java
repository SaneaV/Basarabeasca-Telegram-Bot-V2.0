package md.basarabeasca.bot.action.util.keyboard;

import static java.util.Collections.singletonList;
import static java.util.List.of;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.NEXT_PAGE;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.PREVIOUS_PAGE;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineKeyboardMarkupUtil {

  private static final String LEFT_ARROW = "⬅️";
  private static final String RIGHT_ARROW = "➡️";
  private static final String SPACE = " ";

  public static InlineKeyboardMarkup getSendInlineKeyboardWithUrl(String text, String url) {
    final InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

    final InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
        .url(url)
        .text(text)
        .build();

    final List<InlineKeyboardButton> rowInline = new ArrayList<>(
        singletonList(inlineKeyboardButton));
    final List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(singletonList(rowInline));

    markupInline.setKeyboard(rowsInline);
    return markupInline;
  }

  public static InlineKeyboardMarkup getSendInlineKeyboardForShowNumber(String text,
      String callBackData, Long page) {
    final InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

    final InlineKeyboardButton previousPage = InlineKeyboardButton.builder()
        .callbackData(PREVIOUS_PAGE.name() + SPACE + page)
        .text(LEFT_ARROW)
        .build();

    final InlineKeyboardButton nextPage = InlineKeyboardButton.builder()
        .callbackData(NEXT_PAGE.name() + SPACE + page)
        .text(RIGHT_ARROW)
        .build();

    final InlineKeyboardButton findNumber = InlineKeyboardButton.builder()
        .callbackData(callBackData)
        .text(text)
        .build();

    final List<InlineKeyboardButton> rowInlineFirst = new ArrayList<>(of(previousPage, nextPage));
    final List<InlineKeyboardButton> rowInlineSecond = new ArrayList<>(of(findNumber));
    final List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(
        of(rowInlineFirst, rowInlineSecond));

    markupInline.setKeyboard(rowsInline);
    return markupInline;
  }
}
