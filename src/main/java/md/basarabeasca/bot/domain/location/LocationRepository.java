package md.basarabeasca.bot.domain.location;

import java.util.List;
import md.basarabeasca.bot.infrastructure.jpa.LocationJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationJpa, Long> {

  List<LocationJpa> findByName(String name);
}
