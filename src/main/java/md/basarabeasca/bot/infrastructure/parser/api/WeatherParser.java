package md.basarabeasca.bot.infrastructure.parser.api;

import java.io.IOException;
import java.util.List;
import md.basarabeasca.bot.domain.weather.Weather;

public interface WeatherParser {

  List<Weather> getWeather() throws IOException;
}
