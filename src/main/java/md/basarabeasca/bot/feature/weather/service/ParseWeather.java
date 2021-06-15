package md.basarabeasca.bot.feature.weather.service;

import md.basarabeasca.bot.feature.weather.dto.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.round;
import static md.basarabeasca.bot.settings.StringUtil.DATE;
import static md.basarabeasca.bot.settings.StringUtil.TEMPERATURE_DAY;
import static md.basarabeasca.bot.settings.StringUtil.TEMPERATURE_NIGHT;
import static md.basarabeasca.bot.settings.StringUtil.WEATHER_DESCRIPTION;
import static md.basarabeasca.bot.settings.StringUtil.WEATHER_IS_UNAVAILABLE;

@Component
public class ParseWeather implements JsonParser {

    @Value("${weather.site}")
    private String site;
    @Value("${weather.appid}")
    private String appId;

    @Override
    public String getWeather() throws IOException {
        return parseToSendMessage(
                Objects.requireNonNull(parseToPojoObject(
                        readJsonFromUrl(site + appId)
                ))
        );
    }

    private String parseToSendMessage(List<Weather> forecast) {
        if (forecast.isEmpty()) {
            return WEATHER_IS_UNAVAILABLE;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Weather weather :
                    forecast) {
                stringBuilder
                        .append(DATE)
                        .append(weather.getDate())
                        .append(TEMPERATURE_DAY)
                        .append(weather.getTempDay())
                        .append(TEMPERATURE_NIGHT)
                        .append(weather.getTempNight())
                        .append(WEATHER_DESCRIPTION)

                        .append(Character
                                .toUpperCase(weather
                                        .getDescription()
                                        .charAt(0)))
                        .append(weather
                                .getDescription()
                                .substring(1))

                        .append("\n\n");
            }
            return stringBuilder.toString();
        }
    }

    private List<Weather> parseToPojoObject(JSONObject json) {

        if (json.isEmpty()) {
            return null;
        } else {
            JSONArray jsonArray = json.getJSONArray("daily");
            List<Weather> forecast = new ArrayList<>();
            final DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (Object object :
                    jsonArray) {

                JSONObject jsonObject = (JSONObject) object;

                Weather weather = Weather.builder()
                        .date(Instant
                                .ofEpochSecond(jsonObject
                                        .getLong("dt"))
                                .atZone(ZoneId.of("GMT-3"))
                                .format(formatter))

                        .tempDay(String
                                .valueOf(round(
                                        jsonObject
                                                .getJSONObject("temp")
                                                .getDouble("day"))))

                        .tempNight(String
                                .valueOf(round(
                                        jsonObject
                                                .getJSONObject("temp")
                                                .getDouble("night"))))

                        .description(jsonObject
                                .getJSONArray("weather")
                                .getJSONObject(0)
                                .getString("description"))
                        .build();
                forecast.add(weather);
            }
            return forecast;
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
