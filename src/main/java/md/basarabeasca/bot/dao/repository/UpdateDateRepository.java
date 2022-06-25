package md.basarabeasca.bot.dao.repository;

import md.basarabeasca.bot.dao.repository.model.UpdateDateJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateDateRepository extends JpaRepository<UpdateDateJpa, Long> {

  UpdateDateJpa findTopByOrderByIdAsc();
}
