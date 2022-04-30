package md.basarabeasca.bot.action.callback;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public interface CallbackQueryHandler {

    String SEARCH_NUMBER = "Найти номер";
    String POINT = ". ";
    String HYPHEN = " - ";

    List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery);

    CallbackQueryType getHandlerQueryType();
}
