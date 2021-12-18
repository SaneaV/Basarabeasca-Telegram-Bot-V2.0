package md.basarabeasca.bot.action.command;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Command {

    List<? super PartialBotApiMethod<?>> execute(Update update);

    String getCommand();
}
