package md.basarabeasca.bot.action.command;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Command {

    String ERROR = "Произошла ошибка при отправлении сообщения. Пожалуйста, обратитесь к @SaneaV";

    List<? super PartialBotApiMethod<?>> execute(Update update);

    String getCommand();
}
