package md.basarabeasca.bot.dao.mapper;

import md.basarabeasca.bot.dao.domain.UpdateDate;
import md.basarabeasca.bot.dao.repository.model.UpdateDateJpa;

public interface UpdateDateMapper {

  UpdateDate toEntity(UpdateDateJpa updateDateJpa);
}