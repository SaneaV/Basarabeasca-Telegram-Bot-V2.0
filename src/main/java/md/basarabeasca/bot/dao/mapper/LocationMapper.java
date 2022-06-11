package md.basarabeasca.bot.dao.mapper;

import md.basarabeasca.bot.dao.domain.Location;
import md.basarabeasca.bot.dao.repository.model.LocationJpa;

public interface LocationMapper {

  Location toEntity(LocationJpa locationJpa);
}
