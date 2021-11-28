package md.basarabeasca.bot.feature.weather.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class WeatherDto {

    private final String date;
    private final String tempDay;
    private final String tempNight;
    private final String description;
}
