package md.basarabeasca.bot.infrastructure.parser;

import java.io.IOException;
import java.util.List;
import md.basarabeasca.bot.dao.domain.Weather;

public interface WeatherParser {

  List<Weather> getWeather() throws IOException;
}
