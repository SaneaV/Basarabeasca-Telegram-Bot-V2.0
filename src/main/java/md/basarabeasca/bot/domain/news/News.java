package md.basarabeasca.bot.domain.news;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class News implements Serializable {

  private final String name;
  private final String description;
  private final String image;
  private final String link;
}
