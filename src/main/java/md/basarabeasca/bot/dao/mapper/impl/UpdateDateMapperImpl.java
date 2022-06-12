package md.basarabeasca.bot.dao.mapper.impl;

import md.basarabeasca.bot.dao.domain.UpdateDate;
import md.basarabeasca.bot.dao.mapper.UpdateDateMapper;
import md.basarabeasca.bot.dao.repository.model.UpdateDateJpa;
import org.springframework.stereotype.Component;

@Component
public class UpdateDateMapperImpl implements UpdateDateMapper {

  @Override
  public UpdateDate toEntity(UpdateDateJpa updateDateJpa) {
    return UpdateDate.builder()
        .lastUpdateDate(updateDateJpa.getLastUpdateDate())
        .build();
  }
}