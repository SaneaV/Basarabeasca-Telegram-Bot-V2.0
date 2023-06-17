package md.basarabeasca.bot.infrastructure.parser;

import static md.basarabeasca.bot.infrastructure.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import md.basarabeasca.bot.domain.weather.Weather;
import md.basarabeasca.bot.domain.weather.WeatherMapper;
import md.basarabeasca.bot.infrastructure.parser.api.WeatherParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherParserImpl implements WeatherParser {

  private final String site;
  private final String appId;
  private final WeatherMapper weatherMapper;

  public WeatherParserImpl(
      @Value("${weather.site}") String site,
      @Value("${weather.appid}") String appId,
      WeatherMapper weatherMapper) {
    this.site = site;
    this.appId = appId;
    this.weatherMapper = weatherMapper;
  }

  @Override
  public List<Weather> getWeather() throws IOException {
    final String url = site + appId;
    final InputStream weatherInputStream = new URL(url).openStream();
    final String weatherInJsonString = readFromInputStream(weatherInputStream);
    final JSONObject weatherInJsonObject = new JSONObject(weatherInJsonString);
    return weatherMapper.toEntity(weatherInJsonObject);
  }
}
