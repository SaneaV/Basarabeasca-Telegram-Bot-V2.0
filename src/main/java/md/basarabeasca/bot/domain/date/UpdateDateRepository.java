package md.basarabeasca.bot.domain.date;

import md.basarabeasca.bot.infrastructure.jpa.UpdateDateJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateDateRepository extends JpaRepository<UpdateDateJpa, Long> {

  UpdateDateJpa findTopByOrderByIdAsc();
}
