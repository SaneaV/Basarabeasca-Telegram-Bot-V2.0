package md.basarabeasca.bot.feature.lastupdate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "update_date")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDate {

    @Id
    @Column(name = "update_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;
}
