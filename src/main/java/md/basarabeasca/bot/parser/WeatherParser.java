package md.basarabeasca.bot.parser;

import java.io.IOException;
import java.util.List;
import md.basarabeasca.bot.domain.Weather;

public interface WeatherParser {

  List<Weather> getWeather() throws IOException;
}
