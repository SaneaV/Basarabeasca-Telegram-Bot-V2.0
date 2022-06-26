package md.basarabeasca.bot.dao.repository.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fuel_price",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"type"}, name = "UK_TYPE")})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuelJpa {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "type")
  private String type;

  @Column(name = "price")
  private double price;
}
