package md.basarabeasca.bot.feature.hotnumbers.dao.repository;

import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    PhoneNumber findByNumber(String number);
}
