package md.basarabeasca.bot.feature.hotnumbers.dao.repository;

import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    PhoneNumber findByNumber(String number);

    List<PhoneNumber> findByDescriptionIgnoreCaseContaining(String description);
}
