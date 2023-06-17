package md.basarabeasca.bot.infrastructure.mapper;

import md.basarabeasca.bot.domain.date.UpdateDate;
import md.basarabeasca.bot.domain.date.UpdateDateMapper;
import md.basarabeasca.bot.infrastructure.jpa.UpdateDateJpa;
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