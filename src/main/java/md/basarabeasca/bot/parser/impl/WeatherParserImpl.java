package md.basarabeasca.bot.parser.impl;

import static md.basarabeasca.bot.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import md.basarabeasca.bot.domain.Weather;
import md.basarabeasca.bot.domain.converter.WeatherPojoConverter;
import md.basarabeasca.bot.parser.WeatherParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherParserImpl implements WeatherParser {

  private final String site;
  private final String appId;
  private final WeatherPojoConverter weatherPojoConverter;

  public WeatherParserImpl(
      @Value("${weather.site}") String site,
      @Value("${weather.appid}") String appId,
      WeatherPojoConverter weatherPojoConverter) {
    this.site = site;
    this.appId = appId;
    this.weatherPojoConverter = weatherPojoConverter;
  }

  @Override
  public List<Weather> getWeather() throws IOException {
    final String url = site + appId;
    final InputStream weatherInputStream = new URL(url).openStream();
    final String weatherInJsonString = readFromInputStream(weatherInputStream);
    final JSONObject weatherInJsonObject = new JSONObject(weatherInJsonString);
    return weatherPojoConverter.toObject(weatherInJsonObject);
  }
}
