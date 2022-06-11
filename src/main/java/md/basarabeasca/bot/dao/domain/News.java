package md.basarabeasca.bot.dao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class News {

  private final String name;
  private final String description;
  private final String image;
  private final String link;
}
