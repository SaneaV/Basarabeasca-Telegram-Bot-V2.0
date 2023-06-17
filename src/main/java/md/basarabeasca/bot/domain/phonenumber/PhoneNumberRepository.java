package md.basarabeasca.bot.domain.phonenumber;

import java.util.List;
import md.basarabeasca.bot.infrastructure.jpa.PhoneNumberJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberJpa, Long> {

  List<PhoneNumberJpa> findTop10ByIdGreaterThan(Long id);

  List<PhoneNumberJpa> findTop10ByIdLessThanEqualOrderByIdDesc(Long id);

  @Query(value = "SELECT MAX(phone_id)\n"
      + "FROM phone_numbers\n"
      + "WHERE phone_id IN (SELECT phone_id FROM phone_numbers WHERE phone_id > :lastId ORDER BY phone_id LIMIT 10)", nativeQuery = true)
  Long getMaxIdOnPage(@Param("lastId") Long lastId);

  @Query(value = "SELECT MIN(PHONE_ID)\n"
      + "FROM phone_numbers\n"
      + "WHERE phone_id IN (SELECT phone_id FROM phone_numbers WHERE phone_id < :lastId ORDER BY phone_id DESC LIMIT 10)", nativeQuery = true)
  Long getMinIdOnPage(@Param("lastId") Long lastId);

  IdOnly findFirstIdByOrderByIdDesc();

  IdOnly findFirstIdByOrderByIdAsc();

  PhoneNumberJpa findByPhoneNumber(String number);

  List<PhoneNumberJpa> findByDescriptionIgnoreCaseContaining(String description);

  interface IdOnly {

    Long getId();
  }
}