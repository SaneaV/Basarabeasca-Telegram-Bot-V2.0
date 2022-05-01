package md.basarabeasca.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rate {

    private String bankName;
    private String currency;
    private String purchase;
    private String sale;
}
