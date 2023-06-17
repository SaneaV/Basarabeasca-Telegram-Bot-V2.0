package md.basarabeasca.bot.telegram.util.message;

import static lombok.AccessLevel.PRIVATE;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.util.keyboard.ReplyKeyboardMarkupUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@RequiredArgsConstructor(access = PRIVATE)
public class MessageUtil {

  public static SendMessage getSendMessage(Message message, String text) {
    final String chatId = message.getChatId().toString();
    return SendMessage.builder()
        .chatId(chatId)
        .parseMode(MARKDOWN)
        .text(text)
        .build();
  }

  public static SendMessage getSendMessageWithReplyKeyboardMarkup(Message message, String text,
      ReplyKeyboardMarkup keyboardMarkup) {
    final String chatId = message.getChatId().toString();
    return SendMessage.builder()
        .chatId(chatId)
        .text(text)
        .parseMode(MARKDOWN)
        .replyMarkup(keyboardMarkup)
        .build();
  }

  public static SendMessage getSendMessageWithInlineKeyboardMarkup(String chatId, String text,
      InlineKeyboardMarkup keyboardMarkup) {
    return SendMessage.builder()
        .chatId(chatId)
        .text(text)
        .parseMode(MARKDOWN)
        .replyMarkup(keyboardMarkup)
        .build();
  }

  public static SendPhoto getSendPhoto(String chatId, String caption,
      String photo, String parseMode) {
    return SendPhoto.builder()
        .chatId(chatId)
        .photo(new InputFile(photo))
        .parseMode(parseMode)
        .caption(caption)
        .build();
  }

  public static SendMessage getSendMessageToFindAPhoneNumber(String chatId, String text,
      Integer messageIdForReply) {
    final ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard.builder()
        .forceReply(true)
        .build();

    return SendMessage.builder()
        .chatId(chatId)
        .text(text)
        .replyToMessageId(messageIdForReply)
        .replyMarkup(forceReplyKeyboard)
        .build();
  }

  public static SendMessage getSendMessageError(Message message, String text) {
    return getSendMessageWithReplyKeyboardMarkup(message, text, ReplyKeyboardMarkupUtil.getMainReplyKeyboardMarkup());
  }
}
