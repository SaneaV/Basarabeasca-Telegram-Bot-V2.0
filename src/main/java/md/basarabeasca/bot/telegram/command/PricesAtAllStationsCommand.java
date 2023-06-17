package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.facade.api.FuelFacade;
import md.basarabeasca.bot.telegram.command.api.Command;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.util.message.MessageUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class PricesAtAllStationsCommand implements Command {

  private static final String PRICES_AT_ALL_STATIONS = "Цены на всех автозаправках";
  private static final String NOT_AVAILABLE_MESSAGE = "Не удалось получить информацию о ценах на заправках города Басарабяска. Попробуйте позже.";
  private static final String FINAL_MESSAGE = "*В списке не представлена автозаправка CANON, так как не было получено данных о стоимости топлива.*";

  private final FuelFacade fuelFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    return sendFullPriceList(update.getMessage());
  }


  private List<? super PartialBotApiMethod<?>> sendFullPriceList(Message message) {
    final List<String> fuelPriceList = fuelFacade.getAllFuelPriceList();

    if (isEmpty(fuelPriceList)) {
      return singletonList(MessageUtil.getSendMessageWithReplyKeyboardMarkup(message, NOT_AVAILABLE_MESSAGE,
          ReplyKeyboardMarkupUtil.getFuelReplyKeyboardMarkup()));
    }

    final List<? super PartialBotApiMethod<?>> messages = fuelPriceList.stream()
        .map(msg -> new SendMessage(message.getChatId().toString(), msg))
        .collect(toList());

    messages.add(MessageUtil.getSendMessageWithReplyKeyboardMarkup(message, FINAL_MESSAGE,
        ReplyKeyboardMarkupUtil.getFuelReplyKeyboardMarkup()));

    return messages;
  }

  @Override
  public String getCommand() {
    return PRICES_AT_ALL_STATIONS;
  }
}
