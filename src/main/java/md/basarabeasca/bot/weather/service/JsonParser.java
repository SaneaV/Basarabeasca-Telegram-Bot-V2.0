package md.basarabeasca.bot.weather.service;

import md.basarabeasca.bot.settings.Parser;

import java.io.IOException;

public interface JsonParser extends Parser {

    String getWeather() throws IOException;
}
