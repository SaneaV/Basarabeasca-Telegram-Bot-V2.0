package md.basarabeasca.bot.action;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.callback.CallbackQueryFacade;
import md.basarabeasca.bot.action.command.impl.AddNumberCommand;
import md.basarabeasca.bot.action.command.impl.BasTVCommand;
import md.basarabeasca.bot.action.command.impl.DeleteNumberCommand;
import md.basarabeasca.bot.action.command.impl.FeedBackCommand;
import md.basarabeasca.bot.action.command.impl.SearchNumberByDescriptionCommand;
import md.basarabeasca.bot.action.command.impl.ShowNumberCommand;
import md.basarabeasca.bot.action.command.impl.StartCommand;
import md.basarabeasca.bot.action.command.impl.WeatherCommand;
import md.basarabeasca.bot.settings.Command;
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
import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageUnknown;

@Component
@RequiredArgsConstructor
@Lazy
public class DispatcherCommand {

    private final StartCommand startCommand;
    private final WeatherCommand weatherCommand;
    private final ShowNumberCommand showNumberCommand;
    private final AddNumberCommand addNumberCommand;
    private final DeleteNumberCommand deleteNumberCommand;
    private final SearchNumberByDescriptionCommand searchNumberByDescriptionCommand;
    private final BasTVCommand basTVCommand;
    private final FeedBackCommand feedBackCommand;
    private final CallbackQueryFacade callbackQueryFacade;

    @Value("${telegrambot.adminId}")
    public String adminId;

    public SendMessage execute(final Update update) throws Exception {

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
        switch (message.getText()) {
            case Command.START_COMMAND: {
                return startCommand.execute(update);
            }
            case Command.BASTV: {
                return basTVCommand.execute(update);
            }
            case Command.FEEDBACK: {
                return feedBackCommand.execute(update);
            }
            case Command.WEATHER: {
                return weatherCommand.execute(update);
            }
            case Command.SHOW_NUMBERS: {
                return showNumberCommand.execute(update);
            }
            case Command.MAIN_MENU: {
                return sendHelpMessage(message);
            }
        }
        return sendHelpMessage(message);
    }

    private SendMessage sendHelpMessage(final Message message) {
        return getSendMessageUnknown(message.getChatId().toString());
    }
}
