package md.basarabeasca.bot.feature.hotnumbers.dao.repository;

import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    @Query(value = "SELECT * FROM phone_numbers WHERE phone_id >= :lastId LIMIT 10", nativeQuery = true)
    List<PhoneNumber> getNextPage(@Param("lastId") Long lastId);

    @Query(value = "SELECT * FROM phone_numbers WHERE phone_id <= :lastId LIMIT 10", nativeQuery = true)
    List<PhoneNumber> getPreviousPage(@Param("lastId") Long lastId);

    PhoneNumber findByPhoneNumber(String number);

    List<PhoneNumber> findByDescriptionIgnoreCaseContaining(String description);
}
