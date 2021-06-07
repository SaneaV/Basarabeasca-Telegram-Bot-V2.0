package md.basarabeasca.bot.feature.hotnumbers.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "phone_numbers",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"number"}, name = "UK_PHONE_NUMBER_N")}
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneNumber {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Pattern(message = "Phone number should start with zero and contain exactly 9 digits",
            regexp = "0\\d{8}")
    @Column(name = "number")
    String number;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    String description;
}
