package md.basarabeasca.bot.infrastructure.service;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.weather.Weather;
import md.basarabeasca.bot.infrastructure.converter.api.WeatherConverter;
import md.basarabeasca.bot.infrastructure.parser.api.WeatherParser;
import md.basarabeasca.bot.infrastructure.service.api.WeatherService;
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
