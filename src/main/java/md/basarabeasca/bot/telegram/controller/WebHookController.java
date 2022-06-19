package md.basarabeasca.bot.telegram.controller;

import static org.springframework.http.ResponseEntity.ok;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.telegram.bot.BasarabeascaBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
public class WebHookController {

  private final BasarabeascaBot basarabeascaBot;

  @PostMapping(value = "/")
  public ResponseEntity<BotApiMethod<?>> onUpdateReceived(@RequestBody Update update) {
    final BotApiMethod<?> botApiMethod = basarabeascaBot.onWebhookUpdateReceived(update);
    return ok().body(botApiMethod);
  }
}