package md.basarabeasca.bot.infrastructure.service.api;

import java.util.List;
import md.basarabeasca.bot.domain.location.Location;

public interface LocationService {

  List<Location> getLocationOf(String name);
}
