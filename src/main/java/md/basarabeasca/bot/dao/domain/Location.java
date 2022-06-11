package md.basarabeasca.bot.dao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Location {

  private final String name;
  private final String latitude;
  private final String longitude;
}
