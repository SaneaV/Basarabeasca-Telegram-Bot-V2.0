package md.basarabeasca.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface ICommand {
    SendMessage execute(final Update update) throws IOException;
}
