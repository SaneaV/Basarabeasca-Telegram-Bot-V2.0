package md.basarabeasca.bot.action.callback;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageUnknown;

@AllArgsConstructor
@Lazy
@Component
public class CallbackQueryFacade {
    private final List<CallbackQueryHandler> callbackQueryHandlers;

    public SendMessage processCallbackQuery(CallbackQuery usersQuery) {
        CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData().split("\\|")[0]);

        Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream().
                filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

        return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).
                orElse(getSendMessageUnknown(usersQuery.getMessage().getChatId().toString()));
    }
}
