package md.basarabeasca.bot.telegram.command;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
      final Optional<Command> userCommand = commands.stream().
          filter(commandTemp ->
              commandTemp.getCommand().contains(message.getText()) ||
                  message.getText().contains(commandTemp.getCommand()) ||
                  (message.isReply() &&
                      message.getReplyToMessage().getText().contains(commandTemp.getCommand())))
          .findFirst();

      return userCommand.map(commandTemp -> commandTemp.execute(update))
          .orElseThrow(RuntimeException::new);

    } catch (Exception exception) {
      throw new RuntimeException();
    }
  }
}
