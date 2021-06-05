package md.basarabeasca.bot.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class News {

    private String name;
    private String description;
    private String image;
    private String link;
}
