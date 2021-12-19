package md.basarabeasca.bot.feature.lastupdate.service;

import java.time.LocalDate;

public interface UpdateDateService {

    LocalDate getDate();

    void deleteDate(LocalDate lastUpdateDate);

    void updateDate();
}
