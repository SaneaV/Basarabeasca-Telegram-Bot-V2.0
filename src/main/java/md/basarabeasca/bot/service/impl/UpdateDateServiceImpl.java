package md.basarabeasca.bot.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.repository.UpdateDateRepository;
import md.basarabeasca.bot.domain.repository.model.UpdateDateJpa;
import md.basarabeasca.bot.service.UpdateDateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDateServiceImpl implements UpdateDateService {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateRepository updateDateRepository;

  @Override
  public LocalDate getDate() {
    final LocalDate updateDate = updateDateRepository.getDate();

    if (updateDate == null) {
      final UpdateDateJpa currentDate = UpdateDateJpa.builder()
          .lastUpdateDate(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))
          .build();
      updateDateRepository.save(currentDate);
      return currentDate.getLastUpdateDate();
    }

    return updateDate;
  }

  @Override
  public void deleteDate(LocalDate lastUpdateDate) {
    final UpdateDateJpa updateDateJpa = updateDateRepository.findByLastUpdateDate(lastUpdateDate);
    updateDateRepository.delete(updateDateJpa);
  }

  @Override
  public void updateDate() {
    deleteDate(getDate());
    final UpdateDateJpa currentDate = UpdateDateJpa.builder()
        .lastUpdateDate(LocalDate.now())
        .build();
    updateDateRepository.save(currentDate);
  }
}
