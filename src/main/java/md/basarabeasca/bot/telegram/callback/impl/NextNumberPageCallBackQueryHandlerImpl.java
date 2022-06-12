package md.basarabeasca.bot.telegram.callback.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.FIND_NUMBER;
import static md.basarabeasca.bot.telegram.callback.CallbackQueryType.NEXT_PAGE;
import static md.basarabeasca.bot.telegram.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardForShowNumber;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageError;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithInlineKeyboardMarkup;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.facade.PhoneNumberFacade;
import md.basarabeasca.bot.telegram.callback.CallbackQueryHandler;
import md.basarabeasca.bot.telegram.callback.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class NextNumberPageCallBackQueryHandlerImpl implements CallbackQueryHandler {

  public final static String TO_MUCH_REQUESTS = "Слишком много запросов. Повторите попытку позже.";

  private final PhoneNumberFacade phoneNumberFacade;
  private Integer lastDeletion;

  @Override
  public List<? super PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
    final String chatId = callbackQuery.getMessage().getChatId().toString();
    final long lastIdFromCallbackQuery = Long.parseLong(callbackQuery.getData().split(SPACE)[1]);

    final String phoneNumbers = phoneNumberFacade.getNextPage(lastIdFromCallbackQuery);
    final long lastId = phoneNumberFacade.getMaxIdOnPage(lastIdFromCallbackQuery);

    try {
      final Integer currentMessageId = callbackQuery.getMessage().getMessageId();
      if (!currentMessageId.equals(lastDeletion)) {
        lastDeletion = currentMessageId;

        final DeleteMessage deleteMessage = new DeleteMessage(chatId, currentMessageId);
        final SendMessage sendMessage = getSendMessageWithInlineKeyboardMarkup(chatId, phoneNumbers,
            getSendInlineKeyboardForShowNumber(SEARCH_NUMBER, FIND_NUMBER.name(), lastId));

        return asList(deleteMessage, sendMessage);
      } else {
        throw new RuntimeException();
      }
    } catch (RuntimeException exception) {
      return singletonList(getSendMessageError(callbackQuery.getMessage(), TO_MUCH_REQUESTS));
    }
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return NEXT_PAGE;
  }
}
