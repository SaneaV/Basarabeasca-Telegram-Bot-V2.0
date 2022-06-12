package md.basarabeasca.bot.infrastructure.service;

import md.basarabeasca.bot.dao.domain.UpdateDate;

public interface UpdateDateService {

  UpdateDate getUpdateDate();

  void updateDate();
}
