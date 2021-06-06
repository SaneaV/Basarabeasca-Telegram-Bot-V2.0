package md.basarabeasca.bot.command;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.impl.BasTVCommand;
import md.basarabeasca.bot.command.impl.FeedBackCommand;
import md.basarabeasca.bot.command.impl.StartCommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import md.basarabeasca.bot.settings.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@AllArgsConstructor
public class DispatcherCommand {

    private final StartCommand startCommand;
    private final BasTVCommand basTVCommand;
    private final FeedBackCommand feedBackCommand;

    public SendMessage execute(final Update update) throws IOException {
        final Message message = update.getMessage();

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
        }

        return sendHelpMessage(message);
    }

    private SendMessage sendHelpMessage(final Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Чтобы воспользоваться ботом выберите команду из ниже предложенного меню.");
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup(message));
        return sendMessage;
    }
}
