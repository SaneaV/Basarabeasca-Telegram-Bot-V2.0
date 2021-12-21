package md.basarabeasca.bot.feature.lastupdate.service.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.feature.lastupdate.model.UpdateDate;
import md.basarabeasca.bot.feature.lastupdate.repository.UpdateDateRepository;
import md.basarabeasca.bot.feature.lastupdate.service.UpdateDateService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class UpdateDateServiceImpl implements UpdateDateService {

    private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

    private final UpdateDateRepository updateDateRepository;

    @Override
    public LocalDate getDate() {
        final LocalDate updateDate = updateDateRepository.getDate();

        if (updateDate == null) {
            final UpdateDate currentDate = UpdateDate.builder()
                    .lastUpdateDate(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))
                    .build();
            updateDateRepository.save(currentDate);
            return currentDate.getLastUpdateDate();
        }

        return updateDate;
    }

    @Override
    public void deleteDate(LocalDate lastUpdateDate) {
        final UpdateDate updateDate = updateDateRepository.findByLastUpdateDate(lastUpdateDate);
        updateDateRepository.delete(updateDate);
    }

    @Override
    public void updateDate() {
        deleteDate(getDate());
        final UpdateDate currentDate = UpdateDate.builder()
                .lastUpdateDate(LocalDate.now())
                .build();
        updateDateRepository.save(currentDate);
    }
}
