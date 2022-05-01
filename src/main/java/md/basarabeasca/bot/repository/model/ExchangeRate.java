package md.basarabeasca.bot.repository.model;

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
@Table(name = "exchange_rates",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"currency"}, name = "UK_CURRENCY")})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

  @Id
  @Column(name = "exchange_id", updatable = false, nullable = false)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "currency")
  private String currency;

  @Column(name = "value")
  private String value;
}

