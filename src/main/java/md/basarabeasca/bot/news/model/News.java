package md.basarabeasca.bot.news.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class News {

    private String name;
    private String description;
    private String image;
    private String link;
}
