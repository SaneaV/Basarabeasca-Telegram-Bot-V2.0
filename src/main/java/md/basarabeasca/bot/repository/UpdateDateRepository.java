package md.basarabeasca.bot.repository;

import md.basarabeasca.bot.repository.model.UpdateDateJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface UpdateDateRepository extends JpaRepository<UpdateDateJpa, Long> {

    @Query(value = "SELECT last_update_date FROM update_date LIMIT 1", nativeQuery = true)
    LocalDate getDate();

    UpdateDateJpa findByLastUpdateDate(LocalDate lastUpdateDate);
}
