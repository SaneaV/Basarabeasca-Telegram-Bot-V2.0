package md.basarabeasca.bot.infrastructure.mapper;

import md.basarabeasca.bot.domain.location.Location;
import md.basarabeasca.bot.domain.location.LocationMapper;
import md.basarabeasca.bot.infrastructure.jpa.LocationJpa;
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
