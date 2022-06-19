package md.basarabeasca.bot.telegram.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessage;

import java.util.List;
import lombok.Getter;
import md.basarabeasca.bot.infrastructure.facade.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Component
public class AddNumberCommand implements Command {

  private static final String ADD_NUMBER = "/addNumber";
  private static final String NO_ACCESS = "У вас нет подходящих прав для этой команды. Обратитесь к @SaneaV";

  private final PhoneNumberFacade phoneNumberFacade;
  private final String adminId;

  public AddNumberCommand(
      @Value("${telegrambot.adminId}") String adminId,
      PhoneNumberFacade phoneNumberFacade) {
    this.phoneNumberFacade = phoneNumberFacade;
    this.adminId = adminId;
  }

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final String userId = update.getMessage().getFrom().getId().toString();

    if (userId.equals(adminId)) {
      return singletonList(sendAddNumber(update.getMessage()));
    } else {
      return singletonList(getSendMessage(update.getMessage(), NO_ACCESS));
    }
  }

  private BotApiMethod<?> sendAddNumber(Message message) {
    final String result = phoneNumberFacade.addNumber(message.getText());
    return getSendMessage(message, result);
  }

  @Override
  public String getCommand() {
    return ADD_NUMBER;
  }
}
