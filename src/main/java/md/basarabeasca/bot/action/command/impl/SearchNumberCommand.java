package md.basarabeasca.bot.action.command.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.action.util.keyboard.ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup;
import static md.basarabeasca.bot.action.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static org.apache.commons.lang3.StringUtils.LF;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.action.command.Command;
import md.basarabeasca.bot.service.impl.PhoneNumberServiceImpl;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SearchNumberCommand implements Command {

  public final static String SEARCH_NUMBER = "Введите имя/организацию/заведение, чей номер вы ищите";
  public final static String PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND =
      "Список номеров пуст или запрашиваемый вами номер не был найден";
  public final static String POINT = ". ";
  public final static String HYPHEN = " - ";

  private final PhoneNumberServiceImpl phoneNumberService;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return singletonList(sendSearchNumber(update.getMessage()));
  }

  private BotApiMethod<?> sendSearchNumber(Message message) {
    final List<PhoneNumberDto> phoneNumberDtos = phoneNumberService.findByDescription(
        message.getText());

    final StringBuilder stringBuilder = new StringBuilder();
    if (phoneNumberDtos.isEmpty()) {
      stringBuilder.append(PHONE_NUMBER_LIST_IS_EMPTY_OR_NUMBER_WAS_NOT_FOUND);
    } else {
      int i = 0;
      for (PhoneNumberDto phone :
          phoneNumberDtos) {
        stringBuilder
            .append(++i)
            .append(POINT)
            .append(phone.getPhoneNumber())
            .append(HYPHEN)
            .append(phone.getDescription())
            .append(LF);
      }
    }

    return getSendMessageWithReplyKeyboardMarkup(message, stringBuilder.toString(),
        getMainReplyKeyboardMarkup());
  }

  @Override
  public String getCommand() {
    return SEARCH_NUMBER;
  }
}
