package md.basarabeasca.bot.dao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Fuel {

  private final String type;
  private final double price;
}
