package md.basarabeasca.bot.infrastructure.service.impl;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.Weather;
import md.basarabeasca.bot.infrastructure.converter.WeatherConverter;
import md.basarabeasca.bot.infrastructure.parser.WeatherParser;
import md.basarabeasca.bot.infrastructure.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private final WeatherParser weatherParser;
  private final WeatherConverter weatherConverter;

  @Override
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
