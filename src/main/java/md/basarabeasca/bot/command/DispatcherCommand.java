package md.basarabeasca.bot.command;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.impl.AddNumberCommand;
import md.basarabeasca.bot.command.impl.BasTVCommand;
import md.basarabeasca.bot.command.impl.DeleteNumberCommand;
import md.basarabeasca.bot.command.impl.FeedBackCommand;
import md.basarabeasca.bot.command.impl.SearchNumberByDescriptionCommand;
import md.basarabeasca.bot.command.impl.ShowNumberCommand;
import md.basarabeasca.bot.command.impl.StartCommand;
import md.basarabeasca.bot.command.impl.WeatherCommand;
import md.basarabeasca.bot.settings.Command;
import md.basarabeasca.bot.util.keyboard.ReplyKeyboardMarkupUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;

@Component
@AllArgsConstructor
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

    public SendMessage execute(final Update update) throws IOException {
        final Message message = update.getMessage();

        if (message.getFrom().getId().toString().equals("353461713")) {
            if (message.getText().contains("/addNumber")) {
                return addNumberCommand.execute(update);
            }
            if (message.getText().contains("/deleteNumber")) {
                return deleteNumberCommand.execute(update);
            }
        }

        if (message.getText().contains("/searchNumber")) {
            return searchNumberByDescriptionCommand.execute(update);
        }

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
            case Command.SEARCH_NUMBER_BY_DESCRIPTION: {
                return searchNumberByDescriptionCommand.execute(update);
            }
            case Command.MAIN_MENU: {
                return sendHelpMessage(message);
            }
        }

        return sendHelpMessage(message);
    }


    private SendMessage sendHelpMessage(final Message message) {
        return getSendMessageWithReplyKeyboardMarkup(message.getChatId().toString(),
                "Чтобы воспользоваться ботом выберите команду из ниже предложенного меню.",
                ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
    }
}
