package md.basarabeasca.bot.infrastructure.jpa;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "update_date")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDateJpa {

  @Id
  @Column(name = "update_id", updatable = false, nullable = false)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "last_update_date")
  private LocalDate lastUpdateDate;
}
