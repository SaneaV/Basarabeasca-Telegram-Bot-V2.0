package md.basarabeasca.bot.infrastructure.facade;

public interface PhoneNumberFacade {

  String getNextPage(Long startId);

  String getPreviousPage(Long stopId);

  long getMaxIdOnPage(Long startId);

  long getMinIdOnPage(Long stopId);
}
