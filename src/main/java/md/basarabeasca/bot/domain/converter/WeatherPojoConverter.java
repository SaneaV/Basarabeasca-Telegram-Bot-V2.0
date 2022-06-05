package md.basarabeasca.bot.domain.converter;

import java.util.List;
import md.basarabeasca.bot.domain.Weather;
import org.json.JSONObject;

public interface WeatherPojoConverter {

  List<Weather> toObject(JSONObject json);
}
