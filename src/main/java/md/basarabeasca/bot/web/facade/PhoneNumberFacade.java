package md.basarabeasca.bot.web.facade;

public interface PhoneNumberFacade {

  String getNextPage(Long startId);

  long getLastId(Long startId);
}
