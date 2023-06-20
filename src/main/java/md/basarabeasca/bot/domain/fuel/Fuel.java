package md.basarabeasca.bot.domain.fuel;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Fuel implements Serializable {

  private final String station;
  private final String type;
  private final Double price;
}
