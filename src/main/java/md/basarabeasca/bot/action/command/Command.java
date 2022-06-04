package md.basarabeasca.bot.action.command;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

  List<? super PartialBotApiMethod<?>> execute(Update update);

  String getCommand();
}
