package md.basarabeasca.bot.command.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.command.ICommand;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.impl.PhoneNumberServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static md.basarabeasca.bot.util.message.MessageUtil.getSendMessage;

@AllArgsConstructor
@Component
@Lazy
public class ShowNumberCommand implements ICommand {

    private final PhoneNumberServiceImpl phoneNumberService;

    @Override
    public SendMessage execute(Update update) throws IOException {
        return sendShowNumbers(update.getMessage());
    }

    private SendMessage sendShowNumbers(final Message message) {

        List<PhoneNumberDto> phoneNumberDtos = phoneNumberService.getAllNumbers();
        StringBuilder stringBuilder = new StringBuilder();

        if (phoneNumberDtos.isEmpty()) {
            stringBuilder.append("Список номеров пуст");
        } else {
            int i = 0;
            for (PhoneNumberDto phone :
                    phoneNumberDtos) {
                stringBuilder
                        .append(++i)
                        .append(". ")
                        .append(phone.getNumber())
                        .append(" - ")
                        .append(phone.getDescription())
                        .append("\n");
            }

            stringBuilder.append("\nЧтобы найти номер воспользуйтесь коммандой: \n" +
                    "/searchNumber \"Чей номер ищите\"");
        }

        return getSendMessage(message.getChatId().toString(),
                stringBuilder.toString());
    }
}
