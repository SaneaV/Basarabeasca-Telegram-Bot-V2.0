package md.basarabeasca.bot.infrastructure.facade.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.Fuel;
import md.basarabeasca.bot.infrastructure.converter.FuelConverter;
import md.basarabeasca.bot.infrastructure.facade.FuelFacade;
import md.basarabeasca.bot.infrastructure.service.FuelService;
import md.basarabeasca.bot.infrastructure.service.UpdateDateService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelFacadeImpl implements FuelFacade {

  private final FuelService fuelService;
  private final FuelConverter fuelConverter;
  private final UpdateDateService updateDateService;

  @Override
  public String getANREFuelPrice() {
    updateDateService.checkUpToDateInformation();
    final List<Fuel> anreFuelPrice = fuelService.getANREFuelPrice();
    return fuelConverter.toMessage(anreFuelPrice);
  }
}
