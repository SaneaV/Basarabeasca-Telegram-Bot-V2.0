package md.basarabeasca.bot.infrastructure.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.location.Location;
import md.basarabeasca.bot.domain.location.LocationMapper;
import md.basarabeasca.bot.domain.location.LocationRepository;
import md.basarabeasca.bot.infrastructure.jpa.LocationJpa;
import md.basarabeasca.bot.infrastructure.service.api.LocationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  @Override
  public List<Location> getLocationOf(String name) {
    final List<LocationJpa> location = locationRepository.findByName(name);
    return location.stream()
        .map(locationMapper::toEntity)
        .collect(toList());
  }
}
