package md.basarabeasca.bot.telegram.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CommandFacade {

  private final List<Command> commands;

  public List<? super PartialBotApiMethod<?>> processCommand(Update update) {
    try {
      final Message message = update.getMessage();
      return commands.stream()
          .filter(c -> {
            final String command = c.getCommand();
            return isCommand(command, message.getText()) || isReplyCommand(command, message);
          })
          .findFirst()
          .orElseThrow(RuntimeException::new)
          .execute(update);

    } catch (RuntimeException exception) {
      throw new RuntimeException();
    }
  }

  private boolean isCommand(String command, String messageText) {
    return command.contains(messageText) || messageText.contains(command);
  }

  private boolean isReplyCommand(String command, Message message) {
    return message.isReply() && message.getReplyToMessage().getText().contains(command);
  }
}
