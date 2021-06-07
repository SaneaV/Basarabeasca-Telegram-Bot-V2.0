package md.basarabeasca.bot.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
    public String date;
    public String tempDay;
    public String tempNight;
    public String description;
}
