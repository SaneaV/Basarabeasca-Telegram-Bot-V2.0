package md.basarabeasca.bot.action.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessage;

@Component
@RequiredArgsConstructor
public class CallbackQueryFacade {

    public final static String EMPTY_REGEX = " ";
    private static final String ERROR = "Произошла ошибка при отправлении сообщения. Пожалуйста, обратитесь к @SaneaV";

    private final List<CallbackQueryHandler> callbackQueryHandlers;

    public List<? super PartialBotApiMethod<?>> processCallbackQuery(CallbackQuery usersQuery) {
        try {
            final CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData().split(EMPTY_REGEX)[0]);

            final Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream()
                    .filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType))
                    .findFirst();

            return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery))
                    .orElse(singletonList(getSendMessage(usersQuery.getMessage().getChatId().toString(), ERROR)));
        } catch (Exception exception) {
            exception.printStackTrace();
            return singletonList(getSendMessage(usersQuery.getMessage().getChatId().toString(), ERROR));
        }
    }
}
