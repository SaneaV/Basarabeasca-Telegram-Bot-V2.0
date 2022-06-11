package md.basarabeasca.bot.infrastructure.service;

import java.util.List;
import md.basarabeasca.bot.domain.Location;

public interface LocationService {

  List<Location> getLocationOf(String name);
}
