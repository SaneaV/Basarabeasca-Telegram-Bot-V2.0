package md.basarabeasca.bot.domain.weather;

import java.util.List;
import org.json.JSONObject;

public interface WeatherMapper {

  List<Weather> toEntity(JSONObject json);
}
