package md.basarabeasca.bot.action.callback;

import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public interface CallbackQueryHandler {

    String SEARCH_NUMBER = "Найти номер";
    String EMPTY_REGEX = " ";
    String POINT = ". ";
    String HYPHEN = " - ";
    String NEW_LINE = "\n";

    List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery);

    CallbackQueryType getHandlerQueryType();

    default StringBuilder formatPhoneNumbers(List<PhoneNumberDto> phoneNumber, StringBuilder formattedPhones) {
        phoneNumber.forEach(
                number -> {
                    int i = 0;
                    formattedPhones
                            .append(++i)
                            .append(POINT)
                            .append(number.getPhoneNumber())
                            .append(HYPHEN)
                            .append(number.getDescription())
                            .append(NEW_LINE);
                }
        );
        return formattedPhones;
    }
}
