package md.basarabeasca.bot.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import md.basarabeasca.bot.news.model.News;
import md.basarabeasca.bot.news.site.BasTV;
import md.basarabeasca.bot.news.site.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class BasarabeascaBot extends TelegramWebhookBot {

    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.userName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String BOT_TOKEN;

    @Autowired
    private BasTV basTV;
    @Autowired
    private FeedBack feedBack;

    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            String chatId = update.getMessage().getChatId().toString();

            if (message.getText().equalsIgnoreCase("FeedBack") || message.getText().equalsIgnoreCase("BasTV")) {
                List<News> list = null;
                if (message.getText().equalsIgnoreCase("BasTV")) {
                    list = basTV.getLastNews();
                }
                if (message.getText().equalsIgnoreCase("FeedBack")) {
                    list = feedBack.getLastNews();
                }

                assert list != null;
                for (News news : list) {
                    SendPhoto sendPhotoRequest = new SendPhoto();
                    sendPhotoRequest.setChatId(chatId);
                    sendPhotoRequest.setPhoto(new InputFile(news.getImage()));
                    sendPhotoRequest.setParseMode("markdown");
                    sendPhotoRequest.setCaption("*" + news.getName() + "*" + "\n\n" + news.getDescription());

                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setUrl(news.getLink());
                    inlineKeyboardButton.setText("Продолжить чтение");

                    rowInline.add(inlineKeyboardButton);
                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);

                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> keyboard = new ArrayList<>();
                    KeyboardRow row = new KeyboardRow();
                    row.add("FeedBack");
                    row.add("BasTV");
                    keyboard.add(row);
                    keyboardMarkup.setResizeKeyboard(true);
                    keyboardMarkup.setKeyboard(keyboard);
                    sendPhotoRequest.setReplyMarkup(keyboardMarkup);

                    sendPhotoRequest.setReplyMarkup(markupInline);
                    try {
                        execute(sendPhotoRequest);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (message.getText().equalsIgnoreCase("/start")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Добро пожаловать в Бессарабка бот V2.0. " +
                        "Воспользуйтесь клавишами меню, чтобы узнать о новостях в нашем городе.");
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("FeedBack");
                row.add("BasTV");
                keyboard.add(row);
                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
