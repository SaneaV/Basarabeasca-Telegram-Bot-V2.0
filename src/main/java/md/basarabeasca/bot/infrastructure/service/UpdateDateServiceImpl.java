package md.basarabeasca.bot.infrastructure.service;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.date.UpdateDate;
import md.basarabeasca.bot.domain.date.UpdateDateMapper;
import md.basarabeasca.bot.domain.date.UpdateDateRepository;
import md.basarabeasca.bot.infrastructure.jpa.UpdateDateJpa;
import md.basarabeasca.bot.infrastructure.service.api.ExchangeRateService;
import md.basarabeasca.bot.infrastructure.service.api.FuelService;
import md.basarabeasca.bot.infrastructure.service.api.UpdateDateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDateServiceImpl implements UpdateDateService {

  private static final String ZONE_EUROPE_CHISINAU = "Europe/Chisinau";

  private final UpdateDateRepository updateDateRepository;
  private final UpdateDateMapper updateDateMapper;

  private final ExchangeRateService exchangeRateService;
  private final FuelService fuelService;

  @Override
  public void checkUpToDateInformation() {
    final LocalDate lastUpdateDate = getUpdateDate().getLastUpdateDate();
    if (!lastUpdateDate.isEqual(LocalDate.now(ZoneId.of(ZONE_EUROPE_CHISINAU)))) {
      exchangeRateService.updateExchangeRates();
      fuelService.updateANREFuelPrice();
      updateDate();
    }
  }

  private UpdateDate getUpdateDate() {
    final UpdateDateJpa updateDate = getUpdateDateJpas();
    return updateDateMapper.toEntity(updateDate);
  }

  private void updateDate() {
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
