package md.basarabeasca.bot.domain.mapper.impl;

import md.basarabeasca.bot.domain.Location;
import md.basarabeasca.bot.domain.mapper.LocationMapper;
import md.basarabeasca.bot.domain.repository.model.LocationJpa;
import org.springframework.stereotype.Component;

@Component
public class LocationMapperImpl implements LocationMapper {

  @Override
  public Location toEntity(LocationJpa locationJpa) {
    return Location.builder()
        .name(locationJpa.getName())
        .latitude(locationJpa.getLatitude())
        .longitude(locationJpa.getLongitude())
        .build();
  }
}
