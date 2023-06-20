package md.basarabeasca.bot.infrastructure.service;

import static java.util.stream.Collectors.joining;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.J_CACHE_CACHE_MANAGER;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.WEATHER;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.weather.Weather;
import md.basarabeasca.bot.infrastructure.converter.api.WeatherConverter;
import md.basarabeasca.bot.infrastructure.parser.api.WeatherParser;
import md.basarabeasca.bot.infrastructure.service.api.WeatherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private final WeatherParser weatherParser;
  private final WeatherConverter weatherConverter;

  @Override
  @Cacheable(value = WEATHER, cacheManager = J_CACHE_CACHE_MANAGER)
  public String getWeather() {
    try {
      final List<Weather> forecast = weatherParser.getWeather();
      return forecast.stream()
          .map(weatherConverter::toString)
          .collect(joining(EMPTY));
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
