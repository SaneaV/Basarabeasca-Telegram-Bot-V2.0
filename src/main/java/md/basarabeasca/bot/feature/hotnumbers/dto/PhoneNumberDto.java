package md.basarabeasca.bot.feature.hotnumbers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {

    private Long id;
    private String phoneNumber;
    private String description;
}
