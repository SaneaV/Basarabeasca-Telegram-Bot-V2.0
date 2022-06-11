package md.basarabeasca.bot.web.converter;

import md.basarabeasca.bot.dao.domain.Weather;

public interface WeatherStringConverter {

  String toString(Weather weather);
}
