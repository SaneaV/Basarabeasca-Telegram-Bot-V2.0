package md.basarabeasca.bot.domain.location;

import md.basarabeasca.bot.infrastructure.jpa.LocationJpa;

public interface LocationMapper {

  Location toEntity(LocationJpa locationJpa);
}
