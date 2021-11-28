package md.basarabeasca.bot.bot;

import md.basarabeasca.bot.action.DispatcherCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BasarabeascaBot extends TelegramWebhookBot {

    private static final String ERROR_MESSAGE =
            "При отправке сообщения произошла ошибка. Если она повторяется, пожалуйста, обратитесь к @SaneaV";

    private final String webHookPath;
    private final String botUserName;
    private final String botToken;
    private final DispatcherCommand dispatcherCommand;

    @Autowired
    public BasarabeascaBot(
            @Value("${telegrambot.webHookPath}") String webHookPath,
            @Value("${telegrambot.userName}") String botUserName,
            @Value("${telegrambot.botToken}") String botToken,
            DispatcherCommand dispatcherCommand) {
        this.dispatcherCommand = dispatcherCommand;
        this.webHookPath = webHookPath;
        this.botUserName = botUserName;
        this.botToken = botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
            dispatcherCommand.execute(update).forEach(message -> {
                        try {
                            if (message instanceof SendPhoto) {
                                execute((SendPhoto) message);
                            }
                            if (message instanceof DeleteMessage) {
                                execute((DeleteMessage) message);
                            }
                            if (message instanceof SendMessage) {
                                execute((SendMessage) message);
                            }
                        } catch (Exception exception) {
                            try {
                                execute(SendMessage.builder()
                                        .chatId(update.getMessage().getChatId().toString())
                                        .text(ERROR_MESSAGE)
                                        .build());
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
