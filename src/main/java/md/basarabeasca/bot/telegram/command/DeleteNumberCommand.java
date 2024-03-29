package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.sendMessage;

import java.util.List;
import md.basarabeasca.bot.facade.api.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DeleteNumberCommand implements Command {

  private static final String DELETE_NUMBER = "/deleteNumber";
  private static final String NO_ACCESS = "У вас нет подходящих прав для этой команды. Обратитесь к @SaneaV";

  private final PhoneNumberFacade phoneNumberFacade;
  private final String adminId;

  public DeleteNumberCommand(
      @Value("${telegrambot.adminId}") String adminId,
      PhoneNumberFacade phoneNumberFacade) {
    this.phoneNumberFacade = phoneNumberFacade;
    this.adminId = adminId;
  }

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final Message message = update.getMessage();
    final String userId = message.getFrom().getId().toString();

    if (userId.equals(adminId)) {
      final String result = phoneNumberFacade.deleteNumber(message.getText());
      return singletonList(sendMessage(message, result));
    }

    return singletonList(sendMessage(message, NO_ACCESS));
  }

  @Override
  public String getCommand() {
    return DELETE_NUMBER;
  }
}
