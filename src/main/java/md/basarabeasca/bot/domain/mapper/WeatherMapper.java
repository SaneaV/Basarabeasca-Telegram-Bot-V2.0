package md.basarabeasca.bot.domain.mapper;

import java.util.List;
import md.basarabeasca.bot.domain.Weather;
import org.json.JSONObject;

public interface WeatherMapper {

  List<Weather> toEntity(JSONObject json);
}
