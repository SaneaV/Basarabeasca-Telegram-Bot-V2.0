package md.basarabeasca.bot.domain.date;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateDate {

  private final LocalDate lastUpdateDate;
}