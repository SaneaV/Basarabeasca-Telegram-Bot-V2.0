package md.basarabeasca.bot.controller;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.bot.BasarabeascaBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebHookController {
    private final BasarabeascaBot basarabeascaBot;

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return basarabeascaBot.onWebhookUpdateReceived(update);
    }
}