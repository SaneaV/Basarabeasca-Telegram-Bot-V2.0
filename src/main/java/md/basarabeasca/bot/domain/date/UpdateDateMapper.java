package md.basarabeasca.bot.domain.date;

import md.basarabeasca.bot.infrastructure.jpa.UpdateDateJpa;

public interface UpdateDateMapper {

  UpdateDate toEntity(UpdateDateJpa updateDateJpa);
}