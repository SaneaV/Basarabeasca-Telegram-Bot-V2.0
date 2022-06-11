package md.basarabeasca.bot.domain.repository.model;

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
@Table(name = "location", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"latitude", "longitude"}, name = "UK_LOCATION")})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationJpa {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "latitude")
  private String latitude;

  @Column(name = "longitude")
  private String longitude;
}
