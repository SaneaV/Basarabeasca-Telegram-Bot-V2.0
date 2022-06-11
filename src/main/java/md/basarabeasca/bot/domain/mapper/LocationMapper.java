package md.basarabeasca.bot.domain.mapper;

import md.basarabeasca.bot.domain.Location;
import md.basarabeasca.bot.domain.repository.model.LocationJpa;

public interface LocationMapper {

  Location toEntity(LocationJpa locationJpa);
}
