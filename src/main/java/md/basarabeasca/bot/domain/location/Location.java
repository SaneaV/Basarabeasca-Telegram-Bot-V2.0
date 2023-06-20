package md.basarabeasca.bot.domain.location;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Location implements Serializable {

  private final String name;
  private final String latitude;
  private final String longitude;
}
