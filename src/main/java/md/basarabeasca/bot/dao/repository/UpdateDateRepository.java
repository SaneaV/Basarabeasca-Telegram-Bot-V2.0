package md.basarabeasca.bot.dao.repository;

import md.basarabeasca.bot.dao.repository.model.UpdateDateJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UpdateDateRepository extends JpaRepository<UpdateDateJpa, Long> {

  @Query(value = "SELECT * FROM update_date LIMIT 1", nativeQuery = true)
  UpdateDateJpa getUpdateDate();
}
