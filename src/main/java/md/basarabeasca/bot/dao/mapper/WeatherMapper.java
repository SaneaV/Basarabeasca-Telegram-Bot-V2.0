package md.basarabeasca.bot.dao.mapper;

import java.util.List;
import md.basarabeasca.bot.dao.domain.Weather;
import org.json.JSONObject;

public interface WeatherMapper {

  List<Weather> toEntity(JSONObject json);
}
