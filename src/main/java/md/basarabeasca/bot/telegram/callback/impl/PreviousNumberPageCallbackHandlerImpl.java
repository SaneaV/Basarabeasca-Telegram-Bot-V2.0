package md.basarabeasca.bot.telegram.callback.impl;

import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.PREVIOUS_PAGE;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageError;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.callback.CallbackHandler;
import md.basarabeasca.bot.telegram.callback.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class PreviousNumberPageCallbackHandlerImpl implements CallbackHandler {

  private final PhoneNumberFacade phoneNumberFacade;
  private Integer lastDeletion;

  @Override
  public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
    final String chatId = callbackQuery.getMessage().getChatId().toString();
    final long lastIdFromCallbackQuery = Long.parseLong(callbackQuery.getData().split(SPACE)[1]);

    final long lastId = phoneNumberFacade.getMinIdOnPage(lastIdFromCallbackQuery);
    final String phoneNumbers = phoneNumberFacade.getPreviousPage(lastId);

    try {
      final Integer currentMessageId = callbackQuery.getMessage().getMessageId();
      if (!currentMessageId.equals(lastDeletion)) {
        lastDeletion = currentMessageId;
        return sendPhoneNumberMessage(currentMessageId, chatId, phoneNumbers, lastId);
      } else {
        throw new RuntimeException();
      }
    } catch (RuntimeException exception) {
      return singletonList(getSendMessageError(callbackQuery.getMessage(), TO_MUCH_REQUESTS));
    }
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return PREVIOUS_PAGE;
  }
}
