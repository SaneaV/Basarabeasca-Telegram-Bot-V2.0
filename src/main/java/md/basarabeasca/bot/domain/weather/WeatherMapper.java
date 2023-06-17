package md.basarabeasca.bot.domain.weather;

import java.util.List;
import md.basarabeasca.bot.domain.weather.Weather;
import org.json.JSONObject;

public interface WeatherMapper {

  List<Weather> toEntity(JSONObject json);
}
