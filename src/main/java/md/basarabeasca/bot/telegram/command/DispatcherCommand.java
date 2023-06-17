package md.basarabeasca.bot.telegram.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.callback.CallbackQueryFacade;
import md.basarabeasca.bot.telegram.command.CommandFacade;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class DispatcherCommand {

  private final CallbackQueryFacade callbackQueryFacade;
  private final CommandFacade commandFacade;

  public List<? super BotApiMethod<?>> execute(Update update) {
    try {
      if (update.hasCallbackQuery()) {
        return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
      }
      return commandFacade.processCommand(update);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
