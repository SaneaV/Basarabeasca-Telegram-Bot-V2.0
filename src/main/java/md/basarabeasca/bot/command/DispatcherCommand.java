package md.basarabeasca.bot.command;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.impl.AddNumberCommand;
import md.basarabeasca.bot.command.impl.BasTVCommand;
import md.basarabeasca.bot.command.impl.DeleteNumberCommand;
import md.basarabeasca.bot.command.impl.FeedBackCommand;
import md.basarabeasca.bot.command.impl.ShowNumberCommand;
import md.basarabeasca.bot.command.impl.StartCommand;
import md.basarabeasca.bot.command.impl.WeatherCommand;
import md.basarabeasca.bot.keyboard.KeyBoardUtil;
import md.basarabeasca.bot.settings.Command;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@AllArgsConstructor
public class DispatcherCommand {

    @Lazy
    private final StartCommand startCommand;
    @Lazy
    private final BasTVCommand basTVCommand;
    @Lazy
    private final FeedBackCommand feedBackCommand;
    @Lazy
    private final WeatherCommand weatherCommand;
    @Lazy
    private final ShowNumberCommand showNumberCommand;
    @Lazy
    private final AddNumberCommand addNumberCommand;
    @Lazy
    private final DeleteNumberCommand deleteNumberCommand;

    public SendMessage execute(final Update update) throws IOException {
        final Message message = update.getMessage();

        if(message.getFrom().getId().toString().equals("353461713")){
            if(message.getText().contains("/addNumber")){
                return addNumberCommand.execute(update);
            }
            if(message.getText().contains("/deleteNumber")){
                return deleteNumberCommand.execute(update);
            }
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
        }

        return sendHelpMessage(message);
    }

    private SendMessage sendHelpMessage(final Message message) {
        return SendMessage.builder()
                .text("Чтобы воспользоваться ботом выберите команду из ниже предложенного меню.")
                .chatId(message.getChatId().toString())
                .replyMarkup(KeyBoardUtil.getMainReplyKeyboardMarkup())
                .build();
    }
}
