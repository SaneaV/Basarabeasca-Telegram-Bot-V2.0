package md.basarabeasca.bot.domain.converter.impl;

import static java.lang.Math.round;
import static java.time.Instant.ofEpochSecond;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import md.basarabeasca.bot.domain.Weather;
import md.basarabeasca.bot.domain.converter.WeatherPojoConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class WeatherPojoConverterImpl implements WeatherPojoConverter {

  private static final String DAILY = "daily";
  private static final String DATE_FORMAT = "dd-MM-yyyy";
  private static final String DT = "dt";
  private static final String GMT_3 = "GMT-3";
  private static final String TEMP = "temp";
  private static final String DAY = "day";
  private static final String NIGHT = "night";
  private static final String WEATHER = "weather";
  private static final String DESCRIPTION = "description";

  @Override
  public List<Weather> toObject(JSONObject json) {
    if (json.isEmpty()) {
      return null;
    } else {
      final JSONArray jsonArray = json.getJSONArray(DAILY);
      final List<Weather> forecast = new ArrayList<>();
      final DateTimeFormatter formatter = ofPattern(DATE_FORMAT);

      jsonArray.forEach(object -> {
        final JSONObject jsonObject = (JSONObject) object;
        final JSONObject temperatures = jsonObject.getJSONObject(TEMP);

        final Weather weather = Weather.builder()
            .date(getDate(jsonObject, formatter))
            .daytimeTemperature(getTemperature(temperatures, DAY))
            .nightTemperature(getTemperature(temperatures, NIGHT))
            .description(getDescription(jsonObject))
            .build();

        forecast.add(weather);
      });
      return forecast;
    }
  }

  private String getDate(JSONObject jsonObject, DateTimeFormatter formatter) {
    return ofEpochSecond(jsonObject.getLong(DT)).atZone(ZoneId.of(GMT_3)).format(formatter);
  }

  private String getTemperature(JSONObject temperatures, String partOfDay) {
    return String.valueOf(round(temperatures.getDouble(partOfDay)));
  }

  private String getDescription(JSONObject jsonObject) {
    return jsonObject.getJSONArray(WEATHER).getJSONObject(0).getString(DESCRIPTION);
  }
}
