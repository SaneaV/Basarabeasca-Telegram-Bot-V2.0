package md.basarabeasca.bot.telegram.callback;

import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.valueOf;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class CallbackQueryFacade {

  private final List<CallbackHandler> callbackHandlers;

  public List<? super PartialBotApiMethod<?>> processCallbackQuery(CallbackQuery usersQuery) {
    try {
      final CallbackQueryType usersQueryType = valueOf(usersQuery.getData().split(SPACE)[0]);

      return callbackHandlers.stream()
          .filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType))
          .findFirst()
          .orElseThrow(RuntimeException::new)
          .handleCallbackQuery(usersQuery);

    } catch (RuntimeException exception) {
      throw new RuntimeException();
    }
  }
}
