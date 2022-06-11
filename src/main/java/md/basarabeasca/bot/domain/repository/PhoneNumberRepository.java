package md.basarabeasca.bot.domain.repository;

import java.util.List;
import md.basarabeasca.bot.domain.repository.model.PhoneNumberJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberJpa, Long> {

  @Query(value = "SELECT * FROM phone_numbers WHERE phone_id >= :lastId LIMIT 10", nativeQuery = true)
  List<PhoneNumberJpa> getNextPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT * FROM phone_numbers WHERE phone_id <= :lastId LIMIT 10", nativeQuery = true)
  List<PhoneNumberJpa> getPreviousPage(@Param("lastId") Long lastId);

  PhoneNumberJpa findByPhoneNumber(String number);

  List<PhoneNumberJpa> findByDescriptionIgnoreCaseContaining(String description);
}
