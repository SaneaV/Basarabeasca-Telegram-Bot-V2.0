package md.basarabeasca.bot.telegram.command;

import static md.basarabeasca.bot.telegram.util.keyboard.InlineKeyboardMarkupUtil.getSendInlineKeyboardWithUrl;
import static md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil.getNewsReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendMessageWithReplyKeyboardMarkup;
import static md.basarabeasca.bot.telegram.util.message.MessageUtil.getSendPhoto;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

import java.util.ArrayList;
import java.util.List;
import md.basarabeasca.bot.dao.domain.News;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface NewsSiteCommand extends Command {

  String CAPTION = "*%s*\n\n%s";
  String CONTINUE_READING = "Читать продолжение";

  default List<? super PartialBotApiMethod<?>> sendNews(final Message message,
      final List<News> news,
      final String lastTenNews) {
    final List<? super PartialBotApiMethod<?>> messages = new ArrayList<>();

    news.forEach(singleNews -> {
      final SendPhoto sendPhoto = getSendPhoto(message.getChatId().toString(),
          String.format(CAPTION, singleNews.getName(), singleNews.getDescription()),
          singleNews.getImage(),
          MARKDOWN);

      sendPhoto.setReplyMarkup(
          getSendInlineKeyboardWithUrl(CONTINUE_READING, singleNews.getLink()));

      messages.add(sendPhoto);
    });

    messages.add(
        getSendMessageWithReplyKeyboardMarkup(message, lastTenNews, getNewsReplyKeyboardMarkup()));

    return messages;
  }
}
