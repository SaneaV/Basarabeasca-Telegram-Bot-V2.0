package md.basarabeasca.bot.infrastructure.converter;

import md.basarabeasca.bot.dao.domain.Weather;

public interface WeatherConverter {

  String toString(Weather weather);
}
