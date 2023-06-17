package md.basarabeasca.bot.telegram.command;

import static java.util.Collections.singletonList;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.news.News;
import md.basarabeasca.bot.facade.api.NewsFacade;
import md.basarabeasca.bot.telegram.util.keyboard.InlineKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import md.basarabeasca.bot.telegram.util.message.MessageUtil;
import md.basarabeasca.bot.telegram.command.api.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class NewsCommand implements Command {

  //Command
  private static final String CITY_NEWS_TEMPLATE = "Новости города/Новости BasTV/Новости Feedback/Новости Районного совета";
  private static final String CITY_NEWS = "Новости города";
  private static final String BASTV = "Новости BasTV";
  private static final String FEEDBACK = "Новости Feedback";
  private static final String DISTRICT_COUNCIL = "Новости Районного совета";

  //Response
  private static final String WELCOME_MESSAGE = "Воспользуйтесь клавишами ниже, чтобы получить новости с нужного вам сайта.";

  private static final String LAST_10_NEWS_BASTV = "Последние 10 новостей с сайта [Bas-TV](https://bas-tv.md)";
  private static final String LAST_10_NEWS_FEEDBACK = "Последние 10 новостей с сайта [FeedBack](http://feedback.md)";
  private static final String LAST_10_NEWS_DISTRICT_COUNCIL = "Последние 10 новостей с сайта [Районный Совет Басарабяска](https://basarabeasca.md/ru)";

  //Message construction
  private static final String CAPTION = "*%s*\n\n%s";
  private static final String CONTINUE_READING = "Читать продолжение";

  private static final Map<String, String> FINAL_MESSAGE_MAP = new HashMap<>();

  static {
    FINAL_MESSAGE_MAP.put(BASTV, LAST_10_NEWS_BASTV);
    FINAL_MESSAGE_MAP.put(FEEDBACK, LAST_10_NEWS_FEEDBACK);
    FINAL_MESSAGE_MAP.put(DISTRICT_COUNCIL, LAST_10_NEWS_DISTRICT_COUNCIL);
  }

  private final NewsFacade newsFacade;

  @Override
  public List<? super PartialBotApiMethod<?>> execute(Update update) {
    final Message message = update.getMessage();
    final String source = getSource(message.getText());

    return sendStartMessage(message, source);
  }

  @Override
  public String getCommand() {
    return CITY_NEWS_TEMPLATE;
  }

  private List<? super PartialBotApiMethod<?>> sendStartMessage(Message message, String source) {
    if (source.equals(CITY_NEWS)) {
      final SendMessage welcomeMessage = MessageUtil.getSendMessageWithReplyKeyboardMarkup(
          message, WELCOME_MESSAGE, ReplyKeyboardMarkupUtil.getNewsReplyKeyboardMarkup());
      return singletonList(welcomeMessage);
    }

    final List<News> lastNews = newsFacade.getLastNews(source);
    final String finalMessage = FINAL_MESSAGE_MAP.get(source);
    return sendNews(message, lastNews, finalMessage);
  }

  private String getSource(String message) {
    return Stream.of(CITY_NEWS, BASTV, DISTRICT_COUNCIL, FEEDBACK)
        .filter(s -> s.equals(message))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }

  private List<? super PartialBotApiMethod<?>> sendNews(Message message, List<News> news,
      String lastTenNews) {
    final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

    news.forEach(n -> {
      final SendPhoto sendPhoto = MessageUtil.getSendPhoto(message.getChatId().toString(),
          String.format(CAPTION, n.getName(), n.getDescription()), n.getImage(), MARKDOWN);

      sendPhoto.setReplyMarkup(InlineKeyboardMarkupUtil.getSendInlineKeyboardWithUrl(CONTINUE_READING, n.getLink()));
      messages.add(sendPhoto);
    });

    messages.add(
        MessageUtil.getSendMessageWithReplyKeyboardMarkup(message, lastTenNews, ReplyKeyboardMarkupUtil.getNewsReplyKeyboardMarkup()));

    return messages;
  }
}
