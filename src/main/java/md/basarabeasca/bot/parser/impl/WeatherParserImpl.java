package md.basarabeasca.bot.parser.impl;

import md.basarabeasca.bot.parser.WeatherParser;
import md.basarabeasca.bot.web.dto.WeatherDto;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.round;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class WeatherParserImpl implements WeatherParser {

    private static final String WEATHER_IS_UNAVAILABLE = "Погода недоступна. Обратитесь к администратору бота: @SaneaV";
    private static final String DATE = "Дата: *";
    private static final String TEMPERATURE_DAY = "*\nТемпература днём: ";
    private static final String TEMPERATURE_NIGHT = "\nТемпература ночью: ";
    private static final String WEATHER_DESCRIPTION = "\nОписание: ";

    private static final String TWO_NEW_LINES = "\n\n";
    private static final String DAILY = "daily";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DT = "dt";
    private static final String GMT_3 = "GMT-3";
    private static final String TEMP = "temp";
    private static final String DAY = "day";
    private static final String NIGHT = "night";
    private static final String WEATHER = "weather";
    private static final String DESCRIPTION = "description";

    private final String site;
    private final String appId;

    public WeatherParserImpl(
            @Value("${weather.site}") String site,
            @Value("${weather.appid}") String appId) {
        this.site = site;
        this.appId = appId;
    }

    @Override
    public String getWeather() throws IOException {
        return parseToSendMessage(
                Objects.requireNonNull(parseToPojoObject(
                        readJsonFromUrl(site + appId)
                ))
        );
    }

    private String parseToSendMessage(List<WeatherDto> forecast) {
        if (forecast.isEmpty()) {
            return WEATHER_IS_UNAVAILABLE;
        } else {
            final StringBuilder stringBuilder = new StringBuilder();
            forecast.forEach(
                    weatherDto ->
                            stringBuilder
                                    .append(DATE)
                                    .append(weatherDto.getDate())
                                    .append(TEMPERATURE_DAY)
                                    .append(weatherDto.getTempDay())
                                    .append(TEMPERATURE_NIGHT)
                                    .append(weatherDto.getTempNight())
                                    .append(WEATHER_DESCRIPTION)

                                    .append(Character
                                            .toUpperCase(weatherDto
                                                    .getDescription()
                                                    .charAt(0)))
                                    .append(weatherDto
                                            .getDescription()
                                            .substring(1))

                                    .append(TWO_NEW_LINES)
            );
            return stringBuilder.toString();
        }
    }

    private List<WeatherDto> parseToPojoObject(JSONObject json) {

        if (json.isEmpty()) {
            return null;
        } else {
            final JSONArray jsonArray = json.getJSONArray(DAILY);
            final List<WeatherDto> forecast = new ArrayList<>();
            final DateTimeFormatter formatter = ofPattern(DATE_FORMAT);

            jsonArray.forEach(
                    object -> {
                        JSONObject jsonObject = (JSONObject) object;

                        WeatherDto weatherDto = WeatherDto.builder()
                                .date(Instant
                                        .ofEpochSecond(jsonObject
                                                .getLong(DT))
                                        .atZone(ZoneId.of(GMT_3))
                                        .format(formatter))

                                .tempDay(String
                                        .valueOf(round(
                                                jsonObject
                                                        .getJSONObject(TEMP)
                                                        .getDouble(DAY))))

                                .tempNight(String
                                        .valueOf(round(
                                                jsonObject
                                                        .getJSONObject(TEMP)
                                                        .getDouble(NIGHT))))

                                .description(jsonObject
                                        .getJSONArray(WEATHER)
                                        .getJSONObject(0)
                                        .getString(DESCRIPTION))
                                .build();
                        forecast.add(weatherDto);
                    }
            );
            return forecast;
        }

    }

    private String readAll(Reader rd) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is, UTF_8));
            final String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
