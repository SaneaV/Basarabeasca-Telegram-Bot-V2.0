package md.basarabeasca.bot.action;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.callback.CallbackQueryFacade;
import md.basarabeasca.bot.action.command.CommandFacade;
import md.basarabeasca.bot.action.command.impl.AddNumberCommand;
import md.basarabeasca.bot.action.command.impl.DeleteNumberCommand;
import md.basarabeasca.bot.action.command.impl.SearchNumberByDescriptionCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static md.basarabeasca.bot.settings.Command.ADD_NUMBER;
import static md.basarabeasca.bot.settings.Command.DELETE_NUMBER;
import static md.basarabeasca.bot.settings.StringUtil.SEARCH_NUMBER_CALLBACK_DATA;

@Component
@RequiredArgsConstructor
@Lazy
public class DispatcherCommand {

    private final AddNumberCommand addNumberCommand;
    private final DeleteNumberCommand deleteNumberCommand;
    private final SearchNumberByDescriptionCommand searchNumberByDescriptionCommand;
    private final CallbackQueryFacade callbackQueryFacade;
    private final CommandFacade commandFacade;

    @Value("${telegrambot.adminId}")
    public String adminId;

    public SendMessage execute(final Update update) throws IOException, InterruptedException {

        //CallBack Options
        if (update.hasCallbackQuery()) {
            return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
        }

        final Message message = update.getMessage();

        if (message.isReply()) {
            if (message.getReplyToMessage().getText().contains(SEARCH_NUMBER_CALLBACK_DATA)) {
                return searchNumberByDescriptionCommand.execute(update);
            }
        }

        //Admin Options
        if (message.getFrom().getId().toString().equals(adminId)) {
            if (message.getText().contains(ADD_NUMBER)) {
                return addNumberCommand.execute(update);
            }
            if (message.getText().contains(DELETE_NUMBER)) {
                return deleteNumberCommand.execute(update);
            }
        }

        //Main Commands
        return commandFacade.processCommand(update);
    }
}
