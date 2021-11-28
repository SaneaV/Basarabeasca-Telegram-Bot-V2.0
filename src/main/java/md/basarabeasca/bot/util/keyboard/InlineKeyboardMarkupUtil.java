package md.basarabeasca.bot.util.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static md.basarabeasca.bot.action.callback.CallbackQueryType.NEXT_PAGE;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.PREVIOUS_PAGE;

public class InlineKeyboardMarkupUtil {

    private static final String LEFT_ARROW = "⬅️";
    private static final String RIGHT_ARROW = "➡️";
    private static final String SPACE = " ";

    public static InlineKeyboardMarkup getSendInlineKeyboardWithUrl(String text, String url) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
                .url(url)
                .text(text)
                .build();

        rowInline.add(inlineKeyboardButton);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public static InlineKeyboardMarkup getSendInlineKeyboardForShowNumber(String text, String callBackData, Long page) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton previousPage = InlineKeyboardButton.builder()
                .callbackData(PREVIOUS_PAGE.name() + SPACE + page)
                .text(LEFT_ARROW)
                .build();

        InlineKeyboardButton nextPage = InlineKeyboardButton.builder()
                .callbackData(NEXT_PAGE.name() + SPACE + page)
                .text(RIGHT_ARROW)
                .build();

        InlineKeyboardButton findNumber = InlineKeyboardButton.builder()
                .callbackData(callBackData)
                .text(text)
                .build();

        List<InlineKeyboardButton> rowInlineFirst = new ArrayList<>(List.of(previousPage, nextPage));
        List<InlineKeyboardButton> rowInlineSecond = new ArrayList<>(List.of(findNumber));

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(List.of(rowInlineFirst, rowInlineSecond));

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
