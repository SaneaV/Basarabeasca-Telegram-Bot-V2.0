package md.basarabeasca.bot.domain.weather;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Weather implements Serializable {

  private final String date;
  private final String daytimeTemperature;
  private final String nightTemperature;
  private final String description;
}
