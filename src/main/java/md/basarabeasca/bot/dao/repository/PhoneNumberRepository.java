package md.basarabeasca.bot.dao.repository;

import java.util.List;
import md.basarabeasca.bot.dao.repository.model.PhoneNumberJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberJpa, Long> {

  @Query(value = "SELECT * FROM phone_numbers WHERE phone_id > :lastId LIMIT 10", nativeQuery = true)
  List<PhoneNumberJpa> getNextPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT * FROM phone_numbers WHERE phone_id <= :lastId ORDER BY (phone_id) DESC LIMIT 10 ", nativeQuery = true)
  List<PhoneNumberJpa> getPreviousPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT MAX(phone_id)\n"
      + "FROM phone_numbers\n"
      + "WHERE phone_id IN (SELECT phone_id FROM phone_numbers WHERE phone_id > :lastId ORDER BY phone_id LIMIT 10)", nativeQuery = true)
  Long getMaxIdOnPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT MIN(PHONE_ID)\n"
      + "FROM phone_numbers\n"
      + "WHERE phone_id IN (SELECT phone_id FROM phone_numbers WHERE phone_id < :lastId ORDER BY phone_id DESC LIMIT 10)", nativeQuery = true)
  Long getMinIdOnPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT MAX(phone_id) FROM phone_numbers", nativeQuery = true)
  Long getLastId();

  @Query(value = "SELECT MIN(phone_id) FROM phone_numbers", nativeQuery = true)
  Long getFirstId();

  PhoneNumberJpa findByPhoneNumber(String number);

  List<PhoneNumberJpa> findByDescriptionIgnoreCaseContaining(String description);
}
