package md.basarabeasca.bot.action.command;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessageUnknown;

@AllArgsConstructor
@Lazy
@Component
public class CommandFacade {
    private final List<ICommand> commands;

    public SendMessage processCommand(Update update) {
        Optional<ICommand> userCommand = commands.stream().
                filter(commandTemp -> commandTemp.getCommand().equals(update.getMessage().getText())).findFirst();

        Optional<SendMessage> sendMessage = userCommand.map(commandTemp -> {
            try {
                return commandTemp.execute(update);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });

        return sendMessage.orElse(getSendMessageUnknown(update.getMessage().getChatId().toString()));
    }
}
