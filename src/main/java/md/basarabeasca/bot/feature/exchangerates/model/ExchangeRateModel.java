package md.basarabeasca.bot.feature.exchangerates.model;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "exchange_rates",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"currency"}, name = "UK_CURRENCY")})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateModel {

    @Id
    @Column(name = "exchange_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Currency is mandatory")
    @Column(name = "currency")
    private String currency;

    @NotBlank(message = "Value rate is mandatory")
    @Column(name = "value")
    private String value;
}

