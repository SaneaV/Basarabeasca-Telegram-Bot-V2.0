package md.basarabeasca.bot.feature.weather.service;

import md.basarabeasca.bot.feature.Parser;

import java.io.IOException;

public interface JsonParser extends Parser {

    String getWeather() throws IOException;
}
