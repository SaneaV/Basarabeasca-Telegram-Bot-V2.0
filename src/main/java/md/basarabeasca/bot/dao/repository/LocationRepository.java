package md.basarabeasca.bot.dao.repository;

import java.util.List;
import md.basarabeasca.bot.dao.repository.model.LocationJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationJpa, Long> {

  List<LocationJpa> findByName(String name);
}
