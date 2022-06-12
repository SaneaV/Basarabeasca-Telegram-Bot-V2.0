package md.basarabeasca.bot.infrastructure.facade;

public interface PhoneNumberFacade {

  String getNextPage(Long startId);

  long getLastId(Long startId);
}
