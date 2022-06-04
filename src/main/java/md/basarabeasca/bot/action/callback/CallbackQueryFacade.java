package md.basarabeasca.bot.action.callback;

import static md.basarabeasca.bot.action.callback.CallbackQueryType.valueOf;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class CallbackQueryFacade {

  private final List<CallbackQueryHandler> callbackQueryHandlers;

  public List<? super PartialBotApiMethod<?>> processCallbackQuery(CallbackQuery usersQuery) {
    try {
      final CallbackQueryType usersQueryType = valueOf(usersQuery.getData().split(SPACE)[0]);

      final Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream()
          .filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType))
          .findFirst();

      return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery))
          .orElseThrow(RuntimeException::new);

    } catch (Exception exception) {
      throw new RuntimeException();
    }
  }
}
