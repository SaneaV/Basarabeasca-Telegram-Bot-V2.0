package md.basarabeasca.bot.action.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.callback.CallbackQueryType.valueOf;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessage;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Component
@RequiredArgsConstructor
public class CallbackQueryFacade {

    private static final String ERROR = "Произошла ошибка при отправлении сообщения. Пожалуйста, обратитесь к @SaneaV";

    private final List<CallbackQueryHandler> callbackQueryHandlers;

    public List<? super PartialBotApiMethod<?>> processCallbackQuery(CallbackQuery usersQuery) {
        try {
            final CallbackQueryType usersQueryType = valueOf(usersQuery.getData().split(SPACE)[0]);

            final Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream()
                    .filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType))
                    .findFirst();

            return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery))
                    .orElse(singletonList(getSendMessage(usersQuery.getMessage(), ERROR)));
        } catch (Exception exception) {
            exception.printStackTrace();
            return singletonList(getSendMessage(usersQuery.getMessage(), ERROR));
        }
    }
}
