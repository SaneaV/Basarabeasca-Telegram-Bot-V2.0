package md.basarabeasca.bot.infrastructure.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.UpdateDate;
import md.basarabeasca.bot.dao.mapper.UpdateDateMapper;
import md.basarabeasca.bot.dao.repository.UpdateDateRepository;
import md.basarabeasca.bot.dao.repository.model.UpdateDateJpa;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDateServiceImpl implements UpdateDateService {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateRepository updateDateRepository;
  private final UpdateDateMapper updateDateMapper;

  @Override
  public UpdateDate getUpdateDate() {
    final UpdateDateJpa updateDate = getUpdateDateJpas();
    return updateDateMapper.toEntity(updateDate);
  }

  @Override
  public void updateDate() {
    final UpdateDateJpa updateDate = updateDateRepository.findTopByOrderByIdAsc();
    updateDate.setLastUpdateDate(LocalDate.now());
    updateDateRepository.save(updateDate);
  }

  private UpdateDateJpa getUpdateDateJpas() {
    final UpdateDateJpa updateDate = updateDateRepository.findTopByOrderByIdAsc();

    if (updateDate == null) {
      return addFirstDate();
    }
    return updateDate;
  }

  private UpdateDateJpa addFirstDate() {
    final UpdateDateJpa currentDate = UpdateDateJpa.builder()
        .lastUpdateDate(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))
        .build();
    return updateDateRepository.save(currentDate);
  }
}
