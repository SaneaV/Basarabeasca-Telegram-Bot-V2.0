package md.basarabeasca.bot.infrastructure.converter.api;

import md.basarabeasca.bot.domain.weather.Weather;

public interface WeatherConverter {

  String toString(Weather weather);
}
