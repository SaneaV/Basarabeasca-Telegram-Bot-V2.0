package md.basarabeasca.bot.domain.fuel;

import md.basarabeasca.bot.infrastructure.jpa.FuelJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FuelRepository extends JpaRepository<FuelJpa, Long> {

  @Modifying
  @Query(value = "UPDATE fuel_price SET price = :price WHERE type = :type", nativeQuery = true)
  void updateANREFuelPrice(String type, double price);
}
