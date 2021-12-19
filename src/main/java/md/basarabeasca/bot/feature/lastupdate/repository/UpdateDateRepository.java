package md.basarabeasca.bot.feature.lastupdate.repository;

import md.basarabeasca.bot.feature.lastupdate.model.UpdateDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface UpdateDateRepository extends JpaRepository<UpdateDate, Long> {

    @Query(value = "SELECT last_update_date FROM update_date LIMIT 1", nativeQuery = true)
    LocalDate getDate();

    UpdateDate findByLastUpdateDate(LocalDate lastUpdateDate);

}
